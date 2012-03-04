package com.rvkb.rfs.facets.admin

import com.rvkb.rfs.model.User
import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.developer.ViewImpl

@FacetKey(name="view", profileId="admin", targetObjectType=User.class)
class ViewBuddyAdmin extends ViewImpl {

}