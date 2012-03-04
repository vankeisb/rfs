package com.rvkb.rfs.fileobserver

class FsEventInit implements FsEvent {

    private final FsEntry entry

    FsEventInit(FsEntry entry) {
        this.entry = entry
    }

    FsEntry getEntry() {
        return entry
    }


}
