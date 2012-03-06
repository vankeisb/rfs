package com.rvkb.rfs.dns.facets.user

import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.BaseResolutionFacet
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.ActionBeanContext
import com.rvkb.rfs.dns.facets.FacetCategory
import com.rvkb.rfs.dns.model.DnsUser
import net.sourceforge.stripes.validation.Validate
import woko.hibernate.HibernateStore
import net.sourceforge.stripes.action.StreamingResolution

@FacetKey(name="update", profileId="user")
@Mixin(FacetCategory)
class Update extends BaseResolutionFacet {

    @Validate(required=true)
    String ip

    @Validate(required=true)
    String port

    Resolution getResolution(ActionBeanContext abc) {
        DnsUser u = currentUser
        u.host = ip
        u.port = port
        u.lastUpdated = new Date()
        HibernateStore s = facetContext.woko.objectStore
        s.save(u)
        return new StreamingResolution("text/plain", "http://$ip:$port")
    }

}
