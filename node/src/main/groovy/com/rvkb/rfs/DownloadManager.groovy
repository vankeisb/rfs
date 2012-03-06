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
import org.apache.http.util.EntityUtils
import woko.util.WLogger

class DownloadManager {

    private static final WLogger logger = WLogger.getLogger(DownloadManager.class)

    private final RfsStore store

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

            logger.info "Attempting to download '$relativePath' from '$buddy.username'"

            RfsHttpClient cli = new RfsHttpClient(new DefaultHttpClient())
            try {

                String url = "$buddy.url/download/File${URLEncoder.encode(relativePath)}"
                String targetFile = "$baseDir$relativePath"

                // mkdir if needed
                int lastIndex = targetFile.lastIndexOf("/");
                if (lastIndex>0) {
                    String dir = targetFile[0..lastIndex-1]
                    new File(dir).mkdirs()
                }

                logger.info "Downloading : $url to $targetFile"

                FileTransfer d = store.doInTxWithResult({ st, session ->
                    FileTransfer ft = new FileTransfer(buddy: buddy, relativePath: relativePath, startedOn: new Date(), download: true)
                    store.save(ft)
                    return ft
                } as TxCallbackWithResult)

                HttpContext ctx = null
                try {
                    ctx = cli.login(buddy.url, cfg.username, cfg.password)
                } catch(Exception e) {
                    logger.error "Error while trying to download '$url' from '$buddy.username'", e
                    d.error = true
                    d.finishedOn = new Date()
                    d.errorMsg = e.message
                    store.doInTx({ st, Session session ->
                        store.save(d)
                    } as TxCallback)
                }
                if (ctx) {
                    try {
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
                                EntityUtils.consume(resp.entity)
                            }
                        }
                    } catch(Exception e) {
                        logger.error "Error while trying to download '$url' from '$buddy.username'", e
                        d.error = true
                        d.finishedOn = new Date()
                        d.errorMsg = e.message
                        store.doInTx({ st, Session session ->
                            store.save(d)
                        } as TxCallback)
                    } finally {
                        store.doInTx({ st, Session session ->
                            d = session.get(FileTransfer.class, d.id)
                            if (d) {
                                d.finishedOn = new Date()
                                store.save(d)
                            }
                        } as TxCallback)
                    }
                } else {
                    logger.error "Authentication failed trying to download '$url' from '$buddy.username'"
                    d.error = true
                    d.finishedOn = new Date()
                    d.errorMsg = "Authentication failure"
                    store.doInTx({ st, Session session ->
                        store.save(d)
                    } as TxCallback)
                }
            } finally {
                cli?.close()
            }
        }
    }

    void stop() {

    }

}
