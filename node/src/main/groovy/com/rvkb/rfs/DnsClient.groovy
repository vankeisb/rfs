package com.rvkb.rfs

import com.rvkb.rfs.model.User
import com.rvkb.rfs.model.Config
import com.rvkb.rfs.util.RfsHttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.HttpResponse
import woko.util.WLogger
import org.apache.http.util.EntityUtils
import org.apache.http.protocol.HttpContext

class DnsClient {

    private static final WLogger logger = WLogger.getLogger(DnsClient.class)

    private final Config config

    DnsClient(Config config) {
        this.config = config
    }

    String getUrl(User user) {
        String dnsUrl = config.dns
        def url = user.url
        if (!dnsUrl) {
            // no dns specified, return user url
            logger.info("No DNS configured, returning user's url for '$user.username' : '$url'")
        } else {
            // dns specified, try to get the user url from the dns
            logger.info("DNS configured : '$dnsUrl', looking up URL for user '$user.username'")
            RfsHttpClient cli = new RfsHttpClient(new DefaultHttpClient())
            try {
                cli.get(null, "$dnsUrl/url/$user.username") { HttpResponse resp ->
                    if (resp.statusLine.statusCode==404) {
                        // user does not have any entry in the DNS, use default address
                        logger.info("DNS returned 404 for user '$user.username', returning URL '$url'")
                        EntityUtils.consume(resp.entity)
                    } else {
                        url = cli.responseToString(resp)
                        logger.info("DNS returned url '$url' for user '$user.username'")
                    }
                }
            } catch(Exception e) {
                logger.warn("Exception caught while trying to connect to DNS at url '$dnsUrl', returning user url '$url' for user '$user.username'", e)
            } finally {
                cli.close()
            }
        }
        return url
    }

    String getMyIpAddress() {
        // http://checkip.dyndns.org:8245/
        RfsHttpClient cli = new RfsHttpClient(new DefaultHttpClient())
        String ip = null
        try {
            cli.get(null, "http://checkip.dyndns.org:8245/") { HttpResponse resp ->
                String line = cli.responseToString(resp)
                line = line["<html><head><title>Current IP Check</title></head><body>Current IP Address: ".length()..-1]
                line = line[0..line.indexOf("</body></html>")-1]
                ip = line
            }
        } catch(Exception e) {
            logger.error("Exception caught while contacting the check IP service : http://checkip.dyndns.org:8245/", e)
        } finally {
            cli.close()
        }
        return ip
    }

    String updateMyIp() {
        String dnsUrl = config.dns
        if (!dnsUrl) {
            logger.warn "No DNS configured, cannot update IP"
            return null
        }
        String myIp = myIpAddress
        if (myIp) {
            RfsHttpClient cli = new RfsHttpClient(new DefaultHttpClient())
            try {
                HttpContext ctx = null
                try {
                    ctx = cli.login(dnsUrl, config.username, config.password)
                } catch(Exception e) {
                    logger.error "Error while trying to login to DNS '$dnsUrl'", e
                }
                if (ctx) {
                    try {
                        cli.get(ctx, "$dnsUrl/update?facet.ip=$myIpAddress&facet.port=$config.port") { HttpResponse resp ->
                            try {
                                // TODO check response
                            } finally {
                                EntityUtils.consume(resp.entity)
                            }
                        }
                    } catch(Exception e) {
                        logger.error "Error while trying to update dns '$dnsUrl' with my ip $myIp", e
                    }
                } else {
                    logger.error "Authentication failed on DNS '$dnsUrl'"
                    // TODO error message to the user !
                }
            } finally {
                cli?.close()
            }
        } else {
            logger.warn("Unable to obtain my IP, cannot update")
        }
        return myIp
    }

}
