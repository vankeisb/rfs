package com.rvkb.rfs

import com.rvkb.rfs.model.Config
import com.rvkb.rfs.model.User
import com.rvkb.rfs.util.RfsHttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.protocol.HttpContext
import woko.hibernate.TxCallback
import woko.hibernate.TxCallbackWithResult
import com.rvkb.rfs.fileobserver.*
import org.apache.http.HttpResponse

class Broadcaster {

    final RfsStore store
    RfsHttpClient cli = new RfsHttpClient(new DefaultHttpClient())

    Broadcaster(RfsStore store) {
        this.store = store
    }

    def log(msg) {
        println "*** $msg"
    }

    def broadcast(FsEventInit e) {
        log(e)
    }

    private def doBroadcast(String facetName, FsEvent e, String relativePath) {

        log "File event received, broadcasting ($e)"

        def buddies
        Config config
        store.doInTx({ st,sess ->
            buddies = store.buddies
            config = store.config
        } as TxCallback)
        Thread.start {
            buddies.each { User b ->

                log "Notifying buddy : $b.username"

                // .../created/babz/path/to/file
                HttpContext c = cli.login(b.url, config.username, config.password)
                if (c) {
                    String url = "$b.url/$facetName?facet.path=${URLEncoder.encode(relativePath)}"
                    println "Authenticated with buddy : $b.username : notifying url $url"
                    cli.get(c, url) { HttpResponse resp ->
                        println cli.responseToString(resp)
                    }
                } else {
                    println "Could not authenticate with buddy : $b.username"
                }
            }
        }

    }

    def broadcast(FsEventCreated e) {
        doBroadcast("created", e, e.entry.relativePath)
    }

    def broadcast(FsEventUpdated e) {
        doBroadcast("updated", e, e.entry.relativePath)
    }

    def broadcast(FsEventDeleted e) {
        Config config = store.doInTxWithResult({ st,sess ->
            store.config
        } as TxCallbackWithResult)
        doBroadcast("deleted", e, e.getRelativePath(config.baseDir))
    }

    void close() {
        cli?.close()
    }

}
