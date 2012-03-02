package com.rvkb.rfs.facets.all

import com.rvkb.rfs.model.Config
import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.all.RenderPropertiesEditImpl
import net.sourceforge.jfacets.annotations.FacetKeyList

@FacetKeyList(keys=[
    @FacetKey(name="renderPropertiesEdit", profileId="guest", targetObjectType=Config.class),
    @FacetKey(name="renderPropertiesEdit", profileId="admin", targetObjectType=Config.class)
])
class RenderPropertiesConfigImpl extends RenderPropertiesEditImpl {

    @Override
    List<String> getPropertyNames() {
        ["baseDir"]
    }


}