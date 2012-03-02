package com.rvkb.rfs.facets

import woko.facets.BaseFacet
import com.rvkb.rfs.RfsStore
import com.rvkb.rfs.model.Config
import javax.servlet.http.HttpServletRequest

@Category(BaseFacet.class)
class FacetCategory {

    boolean checkLocalhost() {
        InetAddress addr = InetAddress.getByName(facetContext.request.remoteAddr)
        // Check if the address is a valid special local or loop back
        if (addr.isAnyLocalAddress() || addr.isLoopbackAddress())
            return true

        // Check if the address is defined on any interface
        try {
            return NetworkInterface.getByInetAddress(addr) != null
        } catch (SocketException e) {
            return false
        }
    }


    RfsStore getStore() {
        return (RfsStore)facetContext.woko.objectStore
    }

    Config getRfsConfig() {
        return store.config
    }

    def getTargetObject() {
        return facetContext.targetObject
    }

}
