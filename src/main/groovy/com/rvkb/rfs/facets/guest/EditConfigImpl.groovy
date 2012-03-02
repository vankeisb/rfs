package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.Config
import woko.facets.builtin.developer.EditImpl
import woko.facets.builtin.Edit
import net.sourceforge.jfacets.IInstanceFacet
import com.rvkb.rfs.facets.admin.EditConfigAdmin
import com.rvkb.rfs.facets.FacetCategory

@FacetKey(name="edit", profileId="guest", targetObjectType=Config.class)
@Mixin(FacetCategory)
class EditConfigImpl extends EditConfigAdmin implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        checkLocalhost()
    }

}