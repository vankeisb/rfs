package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.Buddy
import woko.facets.builtin.developer.EditImpl
import woko.facets.builtin.Edit

@FacetKey(name="edit", profileId="guest", targetObjectType=Buddy.class)
class EditBuddyImpl extends EditImpl implements Edit {

}