package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.Config
import woko.facets.builtin.Home
import net.sourceforge.stripes.action.ActionBeanContext
import com.rvkb.rfs.Rfs
import com.rvkb.rfs.RfsInitListener
import net.sourceforge.stripes.action.LocalizableMessage
import com.rvkb.rfs.facets.admin.SaveConfigAdmin
import com.rvkb.rfs.facets.FacetCategory
import net.sourceforge.jfacets.IInstanceFacet

@FacetKey(name="save", profileId="guest", targetObjectType=Config.class)
@Mixin(FacetCategory)
class SaveConfigImpl extends SaveConfigAdmin implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        checkLocalhost()
    }

}