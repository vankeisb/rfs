package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.User
import com.rvkb.rfs.facets.admin.SaveBuddyAdmin
import net.sourceforge.jfacets.IInstanceFacet
import com.rvkb.rfs.facets.FacetCategory

@FacetKey(name="save", profileId="guest", targetObjectType=User.class)
@Mixin(FacetCategory)
class SaveBuddyImpl extends SaveBuddyAdmin implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        checkLocalhost()
    }

}