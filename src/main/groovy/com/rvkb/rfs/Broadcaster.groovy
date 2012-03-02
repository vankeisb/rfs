package com.rvkb.rfs

import com.rvkb.rfs.fileobserver.FsEventCreated
import com.rvkb.rfs.fileobserver.FsEventUpdated
import com.rvkb.rfs.fileobserver.FsEventDeleted

import com.rvkb.rfs.fileobserver.FsEventInit

class Broadcaster {

    final RfsStore store

    Broadcaster(RfsStore store) {
        this.store = store
    }

    def log(msg) {
        println "*** $msg"
    }

    def broadcast(FsEventInit e) {
        log(e)
    }

    def broadcast(FsEventCreated e) {
        log(e)
//        def buddies = store.doInTxWithResult({ st,sess ->
//            store.getAllBuddies()
//        })
//        buddies.each { User b ->
            // sent HTTP req with
            // file path and md5
//            HttpGet req = new HttpGet("${b.url}/created/")

    }

    def broadcast(FsEventUpdated e) {
        log(e)
    }

    def broadcast(FsEventDeleted e) {
        log(e)
    }

}
