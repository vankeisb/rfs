package com.rvkb.rfs.facets.all

import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.BaseResolutionFacet
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.ActionBeanContext
import com.rvkb.rfs.DnsClient
import com.rvkb.rfs.facets.FacetCategory
import net.sourceforge.stripes.action.StreamingResolution

@FacetKey(name="ip", profileId="all")
@Mixin(FacetCategory)
class ShowIp extends BaseResolutionFacet {

    Resolution getResolution(ActionBeanContext abc) {
        DnsClient dns = new DnsClient(store.config)
        return new StreamingResolution("text/plain", dns.myIpAddress)
    }


}
