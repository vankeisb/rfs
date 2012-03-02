package com.rvkb.rfs

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import com.rvkb.rfs.model.User
import org.apache.http.HttpResponse
import org.apache.http.client.ResponseHandler
import woko.hibernate.TxCallbackWithResult
import com.rvkb.rfs.model.Config

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

            HttpGet get = new HttpGet(url)
            httpclient.execute(get, { HttpResponse r ->
                // write the stream to local file
                new File(targetFile).withOutputStream { os ->
                    os << r.entity.content
                }
            } as ResponseHandler)
        }

    }

    void stop() {

    }

}
