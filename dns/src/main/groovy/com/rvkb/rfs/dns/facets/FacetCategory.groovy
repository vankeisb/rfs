package com.rvkb.rfs.dns.facets

import woko.Woko
import woko.ext.usermanagement.core.DatabaseUserManager
import woko.facets.BaseFacet
import com.rvkb.rfs.dns.model.DnsUser

@Category(BaseFacet.class)
class FacetCategory {

    DnsUser getCurrentUser() {
        Woko woko = facetContext.woko
        DatabaseUserManager um = woko.userManager
        String userName = woko.getUsername(facetContext.request)
        return um.getUserByUsername(userName)
    }

}
