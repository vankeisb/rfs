package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey

import woko.facets.builtin.developer.ViewImpl
import woko.facets.builtin.View
import com.rvkb.rfs.model.User
import com.rvkb.rfs.facets.admin.ViewBuddyAdmin
import net.sourceforge.jfacets.IInstanceFacet
import com.rvkb.rfs.facets.FacetCategory

@FacetKey(name="view", profileId="guest", targetObjectType=User.class)
@Mixin(FacetCategory)
class ViewBuddyImpl extends ViewBuddyAdmin implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        checkLocalhost()
    }

}