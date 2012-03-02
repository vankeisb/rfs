package com.rvkb.rfs.facets.all

import com.rvkb.rfs.model.User
import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.all.RenderPropertiesImpl
import net.sourceforge.jfacets.annotations.FacetKeyList

@FacetKeyList(keys=[
    @FacetKey(name="renderProperties", profileId="guest", targetObjectType=User.class),
    @FacetKey(name="renderProperties", profileId="admin", targetObjectType=User.class)
])
class RenderPropertiesBuddyImpl extends RenderPropertiesImpl {

    @Override
    List<String> getPropertyNames() {
        ["url", "username", "password"]
    }


}