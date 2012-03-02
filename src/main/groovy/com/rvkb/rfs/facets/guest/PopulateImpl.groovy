package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.BaseResolutionFacet
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.ActionBeanContext
import com.rvkb.rfs.model.Buddy
import com.rvkb.rfs.facets.FacetCategory
import net.sourceforge.stripes.action.RedirectResolution

@FacetKey(name="populate", profileId="all")
@Mixin(FacetCategory)
class PopulateImpl extends BaseResolutionFacet {

    Resolution getResolution(ActionBeanContext abc) {
        if (store.buddies.size()==0) {
            store.save new Buddy(name: "kakou", url: "http://kakou.dyndns.org", username: "babz", password: "babz")
            store.save new Buddy(name: "alex", url: "http://boissduzen.dyndns.org", username: "babz", password: "babz")
            store.save new Buddy(name: "mat", url: "http://marache.org/rfs", username: "babz", password: "babz")
        }
        return new RedirectResolution("/home")

    }


}