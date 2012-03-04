package com.rvkb.rfs.facets.admin

import com.rvkb.rfs.model.Config
import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.developer.EditImpl

@FacetKey(name="edit", profileId="admin", targetObjectType=Config.class)
class EditConfigAdmin extends EditImpl {

}