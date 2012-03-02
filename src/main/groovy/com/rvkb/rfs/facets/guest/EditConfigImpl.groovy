package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.Config
import woko.facets.builtin.developer.EditImpl
import woko.facets.builtin.Edit

@FacetKey(name="edit", profileId="guest", targetObjectType=Config.class)
class EditConfigImpl extends EditImpl implements Edit {

}