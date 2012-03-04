package com.rvkb.rfs

import org.apache.http.impl.client.DefaultHttpClient
import com.rvkb.rfs.model.User
import org.apache.http.HttpResponse
import woko.hibernate.TxCallbackWithResult
import com.rvkb.rfs.model.Config
import woko.hibernate.TxCallback
import com.rvkb.rfs.model.FileTransfer
import org.hibernate.Session
import com.rvkb.rfs.util.RfsHttpClient
import org.apache.http.protocol.HttpContext

class DownloadManager {

    private final RfsStore store
    RfsHttpClient cli = new RfsHttpClient(new DefaultHttpClient())

    DownloadManager(RfsStore store) {
        this.store = store
    }

    Config getConfig() {
        store.doInTxWithResult({ st,sess ->
            store.config
        } as TxCallbackWithResult)
    }

    void download(User buddy, String relativePath) {

        Config cfg = config
        String baseDir = cfg.baseDir

        Thread.start() {

            String url = "$buddy.url/download/File${URLEncoder.encode(relativePath)}"
            String targetFile = "$baseDir$relativePath"

            println "*** Downloading : $url to $targetFile"

            FileTransfer d
            store.doInTx({ st, session ->
                d = new FileTransfer(buddy: buddy, relativePath: relativePath, startedOn: new Date(), download: true)
                store.save(d)
            } as TxCallback)


            HttpContext ctx = cli.login(buddy.url, cfg.username, cfg.password)
            if (ctx) {
                try {
                    // TODO handle errors
                    cli.get(ctx, url) { HttpResponse resp ->
                        // write the stream to local file
                        Writer out = new FileWriter(targetFile)
                        try {
                            resp.entity.content.withReader { r ->
                                out << r
                            }
                        } finally {
                            out.flush()
                            out.close()
                        }
                    }
                } finally {
                    store.doInTx({ st, Session session ->
                        d = session.load(FileTransfer.class, d.id)
                        d.finishedOn = new Date()
                        store.save(d)
                    } as TxCallback)
                }
            } else {
                // TODO store failure
                println "ERROR : login failed"
            }
        }
    }

    void stop() {

    }

    void close() {
        cli?.close()
    }

}
