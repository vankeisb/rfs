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

    String user

    Resolution getResolution(ActionBeanContext abc) {
        def um = facetContext.woko.userManager
        if (store.buddies.size()==0 && user) {
            def port = user=="kakou" ? "9999" : "8080"
            store.save new User(
              username: user,
              url: "http://localhost:$port/rfs",
              password: um.encodePassword(user),
              roles: ["buddy"])

            abc.messages << new SimpleMessage("Populated $user")

        }
        return new RedirectResolution("/home")

    }

}