package com.rvkb.rfs.facets.admin

import com.rvkb.rfs.facets.FacetCategory
import com.rvkb.rfs.model.User
import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.developer.EditImpl

@FacetKey(name="edit", profileId="admin", targetObjectType=User.class)
@Mixin(FacetCategory)
class EditBuddyAdmin extends EditImpl {

}