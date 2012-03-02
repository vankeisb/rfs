package com.rvkb.rfs.facets.all

import com.rvkb.rfs.model.User
import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.all.RenderPropertiesImpl

@FacetKey(name="renderProperties", profileId="all", targetObjectType=User.class)
class RenderPropertiesBuddyImpl extends RenderPropertiesImpl {

    @Override
    List<String> getPropertyNames() {
        ["name", "url", "username", "password"]
    }


}