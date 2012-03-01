package com.rvkb.rfs.fileobserver

class FsEventUpdated implements FsEvent {

    private final File file

    FsEventUpdated(File file) {
        this.file = file
    }

    File getFile() {
        return file
    }

    public String toString ( ) {
        "FsEventUpdated{file='$file'}"
    }

}
