package com.rvkb.rfs.fileobserver

class FsEntry {

    final File baseDir
    final File file
    final boolean justCreated

    final Long lastModified
    final Long lastSize
    final String relativePath

    FsEntry(File baseDir, File file, boolean justCreated) {
        this.baseDir = baseDir
        this.file = file
        this.lastModified = file.lastModified()
        this.lastSize = file.size()
        this.relativePath = file.absolutePath[baseDir.absolutePath.length()..-1]
        this.justCreated = justCreated
    }

    String toString() {
        "{FsEntry baseDir=$baseDir, file=$relativePath, lastModified=$lastModified, lastSize=$lastSize, justCreated=$justCreated}"
    }

}
