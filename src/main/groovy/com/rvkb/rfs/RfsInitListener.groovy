package com.rvkb.rfs

import javax.servlet.ServletContextEvent
import com.rvkb.rfs.fileobserver.FileSystemObserver
import woko.ri.RiWokoInitListener
import woko.persistence.ObjectStore
import woko.util.WLogger
import com.rvkb.rfs.model.Config
import com.rvkb.rfs.fileobserver.FsEvent
import com.rvkb.rfs.fileobserver.FsEventInit
import woko.hibernate.TxCallback
import org.hibernate.Session
import com.rvkb.rfs.fileobserver.FsEventCreated
import com.rvkb.rfs.fileobserver.FsEventDeleted
import com.rvkb.rfs.fileobserver.FsEventUpdated

class RfsInitListener extends RiWokoInitListener {

    static FileSystemObserver FSO

    private static final WLogger logger = WLogger.getLogger(RfsInitListener.class)

    @Override
    protected ObjectStore createObjectStore() {
        List<String> packageNames = getPackageNamesFromConfig(CTX_PARAM_PACKAGE_NAMES)
        logger.info("Scanning packages for Entities : $packageNames")
        RfsStore s = new RfsStore(packageNames)
        Broadcaster b = new Broadcaster(s)

        s.doInTx({ hbs, Session session ->
            session.delete("select f from com.rvkb.rfs.model.File as f")
        } as TxCallback)

        Config config = s.config
        if (config) {
            File f = new File(config.baseDir)
            if (!f.exists()) {
                f.mkdirs()
                logger.info("Created shared dir : $f.absolutePath")
            }
            // init phase
            FSO = new FileSystemObserver([f]).addCallback { FsEvent e ->
                if (e instanceof FsEventInit) {
                    // create the file in the db
                    FsEventInit ei = (FsEventInit)e
                    com.rvkb.rfs.model.File dbFile = new com.rvkb.rfs.model.File(path: ei.entry.file.absolutePath)
                    s.doInTx({ store,session ->
                        s.save(dbFile)
                    } as TxCallback)
                }
            }.
            init().
            addCallback { FsEvent e ->
                if (e instanceof FsEventCreated) {
                    FsEventCreated ec = (FsEventCreated)e
                    com.rvkb.rfs.model.File dbFile = new com.rvkb.rfs.model.File(path: ec.file.absolutePath)
                    s.doInTx({ store,session ->
                        s.save(dbFile)
                    } as TxCallback)
                    b.broadcast(ec)
                } else if (e instanceof FsEventDeleted) {
                    FsEventDeleted ed = (FsEventDeleted)e
                    s.doInTx({ store,session ->
                        s.removeFile(ed.absolutePath)
                    } as TxCallback)
                    b.broadcast(ed)
                } else if (e instanceof FsEventUpdated) {
                    FsEventUpdated eu = (FsEventUpdated)e
                    b.broadcast(eu)
                }
            }.
            start()
        }
        return s
    }

    @Override
    void contextDestroyed(ServletContextEvent e) {
        super.contextDestroyed(e)
        FSO?.stop()
    }


}
