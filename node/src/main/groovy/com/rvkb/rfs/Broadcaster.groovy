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
import org.apache.http.util.EntityUtils
import woko.util.WLogger

class Broadcaster {

    private static final WLogger logger = WLogger.getLogger(Broadcaster.class)

    final RfsStore store

    Broadcaster(RfsStore store) {
        this.store = store
    }

    def broadcast(FsEventInit e) {
        // TODO
        logger.warn("Init event not yet implemented !!!")
    }

    private def doBroadcast(String facetName, FsEvent e, String relativePath) {

        logger.info "File event received, broadcasting ($e)"

        def buddies
        Config config
        store.doInTx({ st,sess ->
            buddies = store.buddies
            config = store.config
        } as TxCallback)
        Thread.start {

            RfsHttpClient cli = new RfsHttpClient(new DefaultHttpClient())
            try {
                buddies.each { User b ->

                    logger.info "Notifying buddy : $b.username"

                    DnsClient dns = new DnsClient(config)
                    String buddyUrl = dns.getUrl(b)

                    HttpContext c = cli.login(buddyUrl, config.username, config.password)
                    if (c) {
                        String url = "$buddyUrl/$facetName?facet.path=${URLEncoder.encode(relativePath)}"
                        logger.info "Authenticated with buddy : $b.username : notifying url $url"
                        cli.get(c, url) { HttpResponse resp ->
                            EntityUtils.consume(resp.entity)
                        }
                    } else {
                        logger.warn "Could not authenticate with buddy : $b.username (event=$e)"
                    }
                }
            } finally {
                cli.close()
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

}
