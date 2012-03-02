package com.rvkb.rfs.facets.admin

import com.rvkb.rfs.facets.FacetCategory
import com.rvkb.rfs.model.User
import net.sourceforge.jfacets.annotations.FacetKey
import net.sourceforge.stripes.action.ActionBeanContext
import net.sourceforge.stripes.action.RedirectResolution
import net.sourceforge.stripes.action.Resolution
import woko.facets.BaseResolutionFacet
import net.sourceforge.stripes.action.SimpleMessage

@FacetKey(name="populate", profileId="admin")
@Mixin(FacetCategory)
class PopulateImpl extends BaseResolutionFacet {

    Resolution getResolution(ActionBeanContext abc) {
        def um = facetContext.woko.userManager
        if (store.buddies.size()==0) {
            store.save new User(
              username: "kakou",
              url: "http://kakou.dyndns.org",
              password: um.encodePassword("kakou"),
              roles: ["buddy"],
              outgoingUsername: "babz",
              outgoingPassword: "babz")

            abc.messages << new SimpleMessage("Populated")

        }
        return new RedirectResolution("/home")

    }

}