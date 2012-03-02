package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.Buddy
import woko.facets.builtin.developer.ViewImpl
import woko.facets.builtin.View

@FacetKey(name="view", profileId="guest", targetObjectType=Buddy.class)
class ViewBuddyImpl extends ViewImpl implements View {

}