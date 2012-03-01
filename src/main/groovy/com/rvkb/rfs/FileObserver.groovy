package com.rvkb.rfs

import javax.servlet.ServletContextListener
import javax.servlet.ServletContextEvent
import com.rvkb.rfs.fileobserver.FileSystemObserver

class FileObserver implements ServletContextListener {

    static FileSystemObserver FSO

    void contextInitialized(ServletContextEvent e) {
        def sharedDir = e.servletContext.getInitParameter("rfs.shared.dir")
        if (!sharedDir) {
            throw new RuntimeException("Could not find context param rfs.shared.dir in web.xml")
        }
        println "Starting with shared dir : $sharedDir"
        File f = new File(sharedDir)
        if (!f.exists()) {
            f.mkdirs()
        }
        FSO = new FileSystemObserver([f]).addCallback {

        }.start()
    }

    void contextDestroyed(ServletContextEvent e) {
        FSO.stop()
    }


}
