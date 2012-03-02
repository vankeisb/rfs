package com.rvkb.rfs.fileobserver

class FsEntry {

    File baseDir

    File file
    Long lastModified
    String md5

    String toString() {
        "{FsEntry baseDir=$baseDir, file=$relativePath}"
    }

    String getRelativePath() {
        return file.absolutePath[baseDir.absolutePath.length()..-1]
    }


}
