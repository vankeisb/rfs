package com.rvkb.rfs

import com.rvkb.rfs.fileobserver.FsEventUpdated
import woko.hibernate.TxCallback
import com.rvkb.rfs.fileobserver.FsEventDeleted
import com.rvkb.rfs.fileobserver.FsEventCreated
import com.rvkb.rfs.fileobserver.FsEvent
import com.rvkb.rfs.fileobserver.FsEventInit
import com.rvkb.rfs.fileobserver.FileSystemObserver
import woko.util.WLogger
import com.rvkb.rfs.model.Config
import woko.hibernate.TxCallbackWithResult

class Rfs {

    private static final WLogger logger = WLogger.getLogger(Rfs.class)

    private final RfsStore store
    private final Broadcaster b
    private FileSystemObserver fileSystemObserver
    private java.io.File baseDir
    private final DownloadManager downloadManager
    private boolean stopped = true

    Rfs(RfsStore store) {
        this.store = store
        b = new Broadcaster(store)
        downloadManager = new DownloadManager(store)
    }

    java.io.File getBaseDir() {
        return baseDir
    }

    public String relativePath(java.io.File f) {
        return relativePath(f.absolutePath)
    }

    public String relativePath(String absolute) {
        return absolute[baseDir.absolutePath.length()..-1]
    }

    def initialize(java.io.File baseDir) {
        this.baseDir = baseDir
        logger.info("initializing with base dir '$baseDir.absolutePath'")
        stop()
        store.doInTx({ hbs, session ->
            store.removeAllFiles()
        } as TxCallback)

        // init and start fs observer
        logger.info("Creating new observer")
        fileSystemObserver = new FileSystemObserver([baseDir]).
            addCallback { FsEvent e ->
                logger.info("Event received : $e")
                if (e instanceof FsEventInit) {
                    // create the file in the db
                    FsEventInit ei = (FsEventInit)e
                    com.rvkb.rfs.model.File dbFile = new com.rvkb.rfs.model.File(path: relativePath(ei.entry.file))
                    store.doInTx({ st,session ->
                        store.removeFile(dbFile.path) // make sure we don't create dups !
                        store.save(dbFile)
                    } as TxCallback)
                    b.broadcast(ei)
                } else if (e instanceof FsEventCreated) {
                    FsEventCreated ec = (FsEventCreated)e
                    com.rvkb.rfs.model.File dbFile = new com.rvkb.rfs.model.File(path: relativePath(ec.entry.file))
                    store.doInTx({ st,session ->
                        store.removeFile(dbFile.path)
                        store.save(dbFile)
                    } as TxCallback)
                    b.broadcast(ec)
                } else if (e instanceof FsEventDeleted) {
                    FsEventDeleted ed = (FsEventDeleted)e
                    store.doInTx({ store,session ->
                        store.removeFile(relativePath(ed.absolutePath))
                    } as TxCallback)
                    b.broadcast(ed)
                } else if (e instanceof FsEventUpdated) {
                    FsEventUpdated eu = (FsEventUpdated)e
                    b.broadcast(eu)
                }
            }.
            init().
            start()

        stopped = false
        // start dns update daemon thread
        Thread.start {
            while(!stopped) {
                Config config = store.doInTxWithResult({st,sess ->
                    return store.config
                } as TxCallbackWithResult)
                DnsClient dns = new DnsClient(config)
                dns.updateMyIp()

                try {
                    Thread.sleep(30000)
                } catch(Exception e) {
                    // let go
                }
            }
        }
    }

    def stop() {
        logger.info("Stopping file observer...")
        fileSystemObserver?.stop()
        logger.info("Stopping download manager...")
        downloadManager?.stop()
        stopped = true
    }

    DownloadManager getDownloadManager() {
        return downloadManager
    }


}
