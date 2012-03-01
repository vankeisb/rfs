package com.rvkb.rfs

import com.rvkb.rfs.fileobserver.FsEventCreated
import com.rvkb.rfs.fileobserver.FsEventUpdated
import com.rvkb.rfs.fileobserver.FsEventDeleted

class Broadcaster {

    final RfsStore store

    Broadcaster(RfsStore store) {
        this.store = store
    }

    def broadcast(FsEventCreated e) {

    }

    def broadcast(FsEventUpdated e) {

    }

    def broadcast(FsEventDeleted e) {

    }

}
