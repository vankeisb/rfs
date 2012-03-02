package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.Buddy
import woko.facets.builtin.all.RenderPropertiesEditImpl
import woko.facets.builtin.RenderProperties

@FacetKey(name="renderPropertiesEdit", profileId="guest", targetObjectType=Buddy.class)
class RenderPropertiesEditBuddyImpl extends RenderPropertiesEditImpl implements RenderProperties {

    @Override
    List<String> getPropertyNames() {
        ["name", "url", "username", "password"]
    }

}