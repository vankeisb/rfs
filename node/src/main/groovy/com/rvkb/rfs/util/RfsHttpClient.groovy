package com.rvkb.rfs.util

import org.apache.http.protocol.HttpContext
import org.apache.http.HttpRequest
import org.apache.http.client.methods.HttpGet
import org.apache.http.HttpResponse
import org.apache.http.util.EntityUtils
import org.apache.http.protocol.BasicHttpContext
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.client.protocol.ClientContext
import org.apache.http.client.HttpClient
import org.apache.http.client.CookieStore
import org.apache.http.client.ResponseHandler

class RfsHttpClient {

    private final HttpClient httpclient

    RfsHttpClient(HttpClient httpclient) {
        this.httpclient = httpclient
    }

    public void get(HttpContext ctx, String url, Closure closure) {
        println "REQ : $url"
        HttpRequest req = new HttpGet(url)
        if (ctx) {
            httpclient.execute(req, closure as ResponseHandler, ctx)
        } else {
            httpclient.execute(req, closure as ResponseHandler)
        }
    }

    public String responseToString(HttpResponse response) {
        StringWriter sw = new StringWriter()
        try {
            response.entity.content.withReader { r->
                sw << r
            }
            sw.flush()
            println "RESP : "
            println sw.toString()
        } finally {
            EntityUtils.consume(response.entity)
        }
        return sw.toString()
    }

    public HttpContext login(String baseUrl, String username, String password) {
        HttpContext ctx = new BasicHttpContext();
        CookieStore cookieStore = new BasicCookieStore();
        ctx.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        get(ctx, "$baseUrl/login?username=$username&password=$password&login=true") { HttpResponse resp ->
            if (resp?.statusLine?.statusCode==401) {
                ctx = null
            }
            EntityUtils.consume(resp.entity)
        }
        return ctx
    }

    public void close() {
        httpclient?.connectionManager?.shutdown()
    }



}
