package com.rvkb.rfs.facets.all

import com.rvkb.rfs.model.User
import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.RenderProperties
import woko.facets.builtin.all.RenderPropertiesEditImpl

@FacetKey(name="renderPropertiesEdit", profileId="all", targetObjectType=User.class)
class RenderPropertiesEditBuddyImpl extends RenderPropertiesEditImpl implements RenderProperties {

    @Override
    List<String> getPropertyNames() {
        ["name", "url", "username", "password"]
    }

}