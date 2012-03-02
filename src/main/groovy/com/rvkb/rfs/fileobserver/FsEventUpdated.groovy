package com.rvkb.rfs.fileobserver

class FsEventUpdated implements FsEvent {

    private final FsEntry entry

    FsEventUpdated(FsEntry entry) {
        this.entry = entry
    }

    FsEntry getEntry() {
        return entry
    }

    public String toString ( ) {
        "FsEventUpdated{entry='$entry'}"
    }

}
