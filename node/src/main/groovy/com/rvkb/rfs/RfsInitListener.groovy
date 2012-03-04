package com.rvkb.rfs

import javax.servlet.ServletContextEvent
import woko.ri.RiWokoInitListener
import woko.persistence.ObjectStore
import woko.util.WLogger
import com.rvkb.rfs.model.Config
import woko.hibernate.TxCallback
import org.hibernate.Session
import woko.hibernate.TxCallbackWithResult
import javax.servlet.ServletContext

class RfsInitListener extends RiWokoInitListener {

    private Rfs rfs

    private static final WLogger logger = WLogger.getLogger(RfsInitListener.class)

    static Rfs getRfs(ServletContext c) {
        return (Rfs)c.getAttribute("rfs")
    }

    @Override
    protected ObjectStore createObjectStore() {
        List<String> packageNames = getPackageNamesFromConfig(CTX_PARAM_PACKAGE_NAMES)
        logger.info("Scanning packages for Entities : $packageNames")
        RfsStore s = new RfsStore(packageNames)

        rfs = new Rfs(s)
        servletContext.setAttribute("rfs", rfs)

        // config init if needed
        Config config = (Config)s.doInTxWithResult({ store, session ->
            def c = s.getConfig()
            if (!c) {
                c = new Config()
                s.save(c)
            }
            return c
        } as TxCallbackWithResult)

        if (config?.baseDir) {
            File f = new File(config.baseDir)
            if (!f.exists()) {
                f.mkdirs()
                logger.info("Created shared dir : $f.absolutePath")
            }
            rfs.initialize(f)
        }
        return s
    }

    @Override
    void contextDestroyed(ServletContextEvent e) {
        super.contextDestroyed(e)
        rfs?.stop()
    }


}
