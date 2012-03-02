package com.rvkb.rfs.facets.all

import com.rvkb.rfs.model.User
import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.RenderProperties
import woko.facets.builtin.all.RenderPropertiesEditImpl
import net.sourceforge.jfacets.annotations.FacetKeyList

@FacetKeyList(keys=[
    @FacetKey(name="renderPropertiesEdit", profileId="guest", targetObjectType=User.class),
    @FacetKey(name="renderPropertiesEdit", profileId="admin", targetObjectType=User.class)
])
class RenderPropertiesEditBuddyImpl extends RenderPropertiesEditImpl implements RenderProperties {

    @Override
    List<String> getPropertyNames() {
        ["url", "username", "password"]
    }

}