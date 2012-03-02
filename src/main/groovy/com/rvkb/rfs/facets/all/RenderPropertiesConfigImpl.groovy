package com.rvkb.rfs.facets.all

import com.rvkb.rfs.model.Config
import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.all.RenderPropertiesEditImpl

@FacetKey(name="renderPropertiesEdit", profileId="all", targetObjectType=Config.class)
class RenderPropertiesConfigImpl extends RenderPropertiesEditImpl {

    @Override
    List<String> getPropertyNames() {
        ["baseDir"]
    }


}