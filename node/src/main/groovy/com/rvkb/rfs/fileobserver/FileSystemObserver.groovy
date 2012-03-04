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

    Map<String,FsEntry> files = [:]

    String genMD5(File f) {
        return new String(md.digest(f.getText("UTF-8").bytes))
    }

    FileSystemObserver addCallback(Closure c) {
        callbacks << c
        this
    }

    FileSystemObserver init() {
        baseDirs.each { baseDir ->
            baseDir.eachFileRecurse { File f ->
                if (!f.directory && !files[f.absolutePath]) {
                    def entry = new FsEntry(baseDir,f, false)
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

    void stop() {
        stop = true
    }

    void start() {
        stop = false
        Thread.start {
            while(!stop) {
                for (File baseDir : baseDirs) {
                    checkFile(baseDir, baseDir)
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

    private void checkFile(File baseDir, File f) {
        try {
            Thread.sleep(1000)
        } catch(InterruptedException e) {
            //println "interrupted"
        }
        if (!stop && f) {
            for (File child : f.listFiles()) {
                checkFile(baseDir, child)
                if (stop) {
                    break
                }
            }
            if (!stop && !f.directory) {
                FsEntry entry = files[f.absolutePath]

                //println "*** checking $f.absolutePath"

                if (entry) {
                    if (entry.justCreated) {

                        //println "*** just created : checking sizes"

                        files.remove(f.absolutePath)
                        FsEntry newEntry
                        if (f.size()==entry.lastSize) {

                            //println "*** just created : stale size, firing created event"

                            // allright, file size hasn't changed and entry
                            // is justCreated : fire the created event
                            newEntry = new FsEntry(baseDir, f, false)
                            fireFileEvent(new FsEventCreated(newEntry))
                        } else {
                            //println "*** just created : size changed, just replace entry"
                            newEntry = new FsEntry(baseDir, f, true)
                        }
                        files[f.absolutePath] = newEntry
                    } else {

                        //println "*** just created == false : checking sizes"

                        // created event has already been fired : check
                        // the file size and see if it's been updated
                        if (f.size()!=entry.lastSize) {

                            //println "*** Size updated, fire update"

                            // different size, fire an update
                            files.remove(f.absolutePath)
                            def newEntry = new FsEntry(baseDir, f, false)
                            files[f.absolutePath] = newEntry
                            fireFileEvent(new FsEventUpdated(newEntry))
                        }
                    }
                } else {

                    //println "*** no entry : creating one"

                    // no entry found : create one but don't fire event yet
                    // we will check in next pass for the length of the file,
                    // and fire only if it hasn't changed
                    files[f.absolutePath] = new FsEntry(baseDir, f, true)
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
        new FileSystemObserver([new File("/tmp/testdata")]).
                init().
                addCallback { evt ->
                    println "Callback : $evt"
                }.
                start()
    }


}
