package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.Buddy
import woko.facets.builtin.all.RenderPropertiesImpl

@FacetKey(name="renderProperties", profileId="guest", targetObjectType=Buddy.class)
class RenderPropertiesBuddyImpl extends RenderPropertiesImpl {

    @Override
    List<String> getPropertyNames() {
        ["name", "url", "username", "password"]
    }


}