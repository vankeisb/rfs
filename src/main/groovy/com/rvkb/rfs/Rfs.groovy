package com.rvkb.rfs

import com.rvkb.rfs.fileobserver.FsEventUpdated
import woko.hibernate.TxCallback
import com.rvkb.rfs.fileobserver.FsEventDeleted
import com.rvkb.rfs.fileobserver.FsEventCreated
import com.rvkb.rfs.fileobserver.FsEvent
import com.rvkb.rfs.fileobserver.FsEventInit
import com.rvkb.rfs.fileobserver.FileSystemObserver
import woko.util.WLogger

class Rfs {

    private static final WLogger logger = WLogger.getLogger(Rfs.class)

    private final RfsStore store
    private final Broadcaster b
    private FileSystemObserver fileSystemObserver
    private File baseDir
    private final DownloadManager downloadManager

    Rfs(RfsStore store) {
        this.store = store
        b = new Broadcaster(store)
        downloadManager = new DownloadManager(store)
    }

    File getBaseDir() {
        return baseDir
    }

    public String relativePath(File f) {
        return relativePath(f.absolutePath)
    }

    public String relativePath(String absolute) {
        return absolute[baseDir.absolutePath.length()..-1]
    }

    def initialize(File baseDir) {
        this.baseDir = baseDir
        logger.info("initializing with base dir '$baseDir.absolutePath'")
        stop()
        store.doInTx({ hbs, session ->
            store.removeAllFiles()
        } as TxCallback)

        // init phase
        logger.info("Creating new observer")
        fileSystemObserver = new FileSystemObserver([baseDir]).
            addCallback { FsEvent e ->
                logger.info("Event received : $e")
                if (e instanceof FsEventInit) {
                    // create the file in the db
                    FsEventInit ei = (FsEventInit)e
                    com.rvkb.rfs.model.File dbFile = new com.rvkb.rfs.model.File(path: relativePath(ei.entry.file))
                    store.doInTx({ store,session ->
                        store.save(dbFile)
                    } as TxCallback)
                    b.broadcast(ei)
                } else if (e instanceof FsEventCreated) {
                    FsEventCreated ec = (FsEventCreated)e
                    com.rvkb.rfs.model.File dbFile = new com.rvkb.rfs.model.File(path: relativePath(ec.entry.file))
                    store.doInTx({ store,session ->
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
    }

    def stop() {
        logger.info("Stopping file observer...")
        fileSystemObserver?.stop()
        logger.info("Stopping download manager...")
        downloadManager?.stop()
    }

    DownloadManager getDownloadManager() {
        return downloadManager
    }


}
