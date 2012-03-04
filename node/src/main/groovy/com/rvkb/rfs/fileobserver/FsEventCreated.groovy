package com.rvkb.rfs.fileobserver

class FsEventCreated implements FsEvent {

    private final FsEntry entry

    FsEventCreated(FsEntry entry) {
        this.entry = entry
    }

    FsEntry getEntry() {
        entry
    }

    public String toString ( ) {
        "FsEventCreated{entry='$entry'}"
    }

}