package com.rvkb.rfs.fileobserver

import java.security.MessageDigest

class FileSystemObserver {

    private final List<File> baseDirs = []
    private boolean stop = true
    private MessageDigest md = MessageDigest.getInstance("MD5");

    private final List<Closure> callbacks = []

    FileSystemObserver(List<File> baseDirs) {
        this.baseDirs.addAll(baseDirs)
    }

    def files = [:]

    String genMD5(File f) {
        return new String(md.digest(f.getText("UTF-8").bytes))
    }

    FileSystemObserver addCallback(Closure c) {
        callbacks << c
        this
    }

    FileSystemObserver init() {
        baseDirs.each {
            it.eachFileRecurse { File f ->
                if (!f.directory && !files[f.absolutePath]) {
                    def entry = createEntry(f)
                    files[f.absolutePath] = entry
                    fireFileEvent(new FsEventInit(entry))
                }
            }
        }
        this
    }

    List<File> getBaseDirs() {
        return baseDirs
    }

    private FsEntry createEntry(File f) {
        return new FsEntry(
            file: f,
            lastModified: f.lastModified(),
            md5: genMD5(f))
    }

    void stop() {
        stop = true
    }

    void start() {
        stop = false
        Thread.start {
            while(!stop) {
                for (File baseDir : baseDirs) {
                    checkFile(baseDir)
                    if (stop) {
                        break
                    }
                    // check for removals...
                    def toBeRemoved = []
                    files.each { String absolutePath, entry->
                        File f = new File(absolutePath)
                        if (!f.exists()) {
                            toBeRemoved << absolutePath
                        }
                    }
                    toBeRemoved.each {
                        files.remove(it)
                        fireFileEvent(new FsEventDeleted(it))
                    }
                }
            }
        }
    }

    private void checkFile(File f) {
        try {
            Thread.sleep(1000)
        } catch(InterruptedException e) {
            println "interrupted"
        }
        if (!stop && f) {
            for (File child : f.listFiles()) {
                checkFile(child)
                if (stop) {
                    break
                }
            }
            if (!stop && !f.directory) {
                // check if we have an entry for this file
                //println "Checking file : $f"
                def entry = files[f.absolutePath]
                if (!entry) {
                    files[f.absolutePath] = createEntry(f)
                    fireFileEvent(new FsEventCreated(f))
                } else {
                    // check date change
                    if (f.lastModified()>entry.lastModified) {
                        files.remove(f.absolutePath)
                        files[f.absolutePath] = createEntry(f)
                        fireFileEvent(new FsEventUpdated(f))
                    }
                }
            }
        }
    }

    private void fireFileEvent(FsEvent evt) {
        for (Closure c : callbacks) {
            c(evt)
        }
    }

    static void main(String[] args) {
        new FileSystemObserver([new File("C:/Users/Remi Vankeisbelck/projects/fileobserver/test-data")]).
                init().
                addCallback { evt ->
                    println "Callback : $evt"
                }.
                start()
    }


}
