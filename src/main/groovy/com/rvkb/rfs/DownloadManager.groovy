package com.rvkb.rfs

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import com.rvkb.rfs.model.User
import org.apache.http.HttpResponse
import org.apache.http.client.ResponseHandler
import woko.hibernate.TxCallbackWithResult
import com.rvkb.rfs.model.Config
import woko.hibernate.TxCallback
import com.rvkb.rfs.model.FileTransfer
import org.hibernate.Session

class DownloadManager {

    private final RfsStore store
    private HttpClient httpclient = new DefaultHttpClient();

    DownloadManager(RfsStore store) {
        this.store = store
    }

    String getBaseDir() {
        Config config = store.doInTxWithResult({ st,sess ->
            store.config
        } as TxCallbackWithResult)
        return config.baseDir
    }

    void download(User buddy, String relativePath) {

        Thread.start() {

            String url = "$buddy.url/download/File$relativePath"
            String targetFile = "$baseDir$relativePath"

            println "*** Downloading : $url to $targetFile"

            FileTransfer d
            store.doInTx({ st, session ->
                d = new FileTransfer(buddy: buddy, relativePath: relativePath, startedOn: new Date(), download: true)
                store.save(d)
            } as TxCallback)

            HttpGet get = new HttpGet(url)
            try {
                httpclient.execute(get, { HttpResponse r ->
                    // write the stream to local file
                    new File(targetFile).withOutputStream { os ->
                        os << r.entity.content
                    }
                } as ResponseHandler)
            } finally {
                store.doInTx({ st, Session session ->
                    d = session.load(FileTransfer.class, d.id)
                    d.finishedOn = new Date()
                    store.save(d)
                } as TxCallback)
            }

        }

    }

    void stop() {

    }

}
