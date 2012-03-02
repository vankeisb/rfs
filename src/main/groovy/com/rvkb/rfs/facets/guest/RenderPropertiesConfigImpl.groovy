package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.Config
import woko.facets.builtin.all.RenderPropertiesImpl
import woko.facets.builtin.RenderProperties
import woko.facets.builtin.all.RenderPropertiesEditImpl

@FacetKey(name="renderPropertiesEdit", profileId="guest", targetObjectType=Config.class)
class RenderPropertiesConfigImpl extends RenderPropertiesEditImpl {

    @Override
    List<String> getPropertyNames() {
        ["baseDir"]
    }


}