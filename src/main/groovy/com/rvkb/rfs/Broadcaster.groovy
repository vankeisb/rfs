package com.rvkb.rfs

import com.rvkb.rfs.fileobserver.FsEventCreated
import com.rvkb.rfs.fileobserver.FsEventUpdated
import com.rvkb.rfs.fileobserver.FsEventDeleted

import com.rvkb.rfs.fileobserver.FsEventInit
import com.rvkb.rfs.model.User
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.HttpClient
import org.apache.http.HttpResponse
import woko.hibernate.TxCallback
import org.apache.http.protocol.BasicHttpContext
import org.apache.http.protocol.HttpContext
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.client.protocol.ClientContext
import org.apache.http.client.CookieStore
import org.apache.http.util.EntityUtils
import org.apache.http.HttpRequest
import com.rvkb.rfs.model.Config
import com.rvkb.rfs.fileobserver.FsEvent
import woko.hibernate.TxCallbackWithResult

class Broadcaster {

    final RfsStore store
    HttpClient httpclient = new DefaultHttpClient();

    Broadcaster(RfsStore store) {
        this.store = store
    }

    def log(msg) {
        println "*** $msg"
    }

    def broadcast(FsEventInit e) {
        log(e)
    }

    private String httpGet(HttpContext ctx, String url) {
        println "REQ : $url"
        HttpRequest req = new HttpGet(url)
        HttpResponse resp = httpclient.execute(req, ctx)
        StringWriter sw = new StringWriter()
        try {
            resp.entity.content.withReader { r->
                sw << r
            }
            sw.flush()
            println "RESP : "
            println sw.toString()
        } finally {
            EntityUtils.consume(resp.entity)
        }
        return sw.toString()
    }

    private HttpContext login(String baseUrl, String username, String password) {
        HttpContext localContext = new BasicHttpContext();
        CookieStore cookieStore = new BasicCookieStore();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        String resp = httpGet(localContext, "$baseUrl/login?username=$username&password=$password&login=true")
        println resp
        return localContext
    }

    private def doBroadcast(String facetName, FsEvent e, String relativePath) {

        println "*** File event received, broadcasting ($e)"

        def buddies
        Config config
        store.doInTx({ st,sess ->
            buddies = store.buddies
            config = store.config
        } as TxCallback)
        Thread.start {
            buddies.each { User b ->

                println "*** Notifying buddy : $b.username"

                // .../created/babz/path/to/file
                HttpContext c = login(b.url, config.username, config.password)
                if (c) {
                    println "*** Authenticated with buddy : $b.username"
                    String res = httpGet(c, "$b.url/$facetName?facet.path=${relativePath}&facet.user=${config.username}")
                    println res
                } else {
                    println "*** Could not authenticate with buddy : $b.username"
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

}
