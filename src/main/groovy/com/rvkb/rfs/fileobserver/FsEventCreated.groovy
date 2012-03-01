package com.rvkb.rfs.fileobserver

class FsEventCreated implements FsEvent {

    private final File file

    FsEventCreated(File file) {
        this.file = file
    }

    File getFile() {
        file
    }

    public String toString ( ) {
        "FsEventCreated{file='$file'}"
    }

}