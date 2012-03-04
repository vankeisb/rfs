package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.User
import woko.facets.builtin.developer.EditImpl
import woko.facets.builtin.Edit
import net.sourceforge.jfacets.IInstanceFacet
import com.rvkb.rfs.facets.FacetCategory
import com.rvkb.rfs.facets.admin.EditBuddyAdmin

@FacetKey(name="edit", profileId="guest", targetObjectType=User.class)
@Mixin(FacetCategory)
class EditBuddyGuest extends EditBuddyAdmin implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        checkLocalhost()
    }


}