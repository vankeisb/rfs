package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey

import woko.facets.builtin.all.HomeImpl
import net.sourceforge.stripes.action.RedirectResolution
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.ActionBeanContext
import net.sourceforge.stripes.action.ForwardResolution
import com.rvkb.rfs.facets.FacetCategory
import com.rvkb.rfs.model.Config
import com.rvkb.rfs.model.User
import com.rvkb.rfs.facets.admin.HomeAdmin
import net.sourceforge.jfacets.IInstanceFacet

@FacetKey(name="home", profileId="guest")
@Mixin(FacetCategory)
class HomeGuest extends HomeAdmin implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        checkLocalhost()
    }

}
