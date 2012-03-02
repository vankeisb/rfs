package com.rvkb.rfs.fileobserver

class FsEventDeleted implements FsEvent {

    private final String absolutePath

    FsEventDeleted(String absolutePath) {
        this.absolutePath = absolutePath
    }

    String getAbsolutePath() {
        return absolutePath
    }

    public String toString ( ) {
        "FsEventDeleted{absolutePath='$absolutePath'}"
    }

    String getRelativePath(String baseDir) {
        return absolutePath[baseDir.length()..-1]
    }

}
