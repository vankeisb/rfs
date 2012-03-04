package com.rvkb.rfs.dns.facets.user

import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.all.HomeImpl
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.ActionBeanContext
import net.sourceforge.stripes.action.ForwardResolution
import com.rvkb.rfs.dns.facets.FacetCategory

@FacetKey(name="home", profileId="user")
@Mixin(FacetCategory)
class HomeUser extends HomeImpl {

    @Override
    Resolution getResolution(ActionBeanContext abc) {
        return new ForwardResolution("/WEB-INF/jsp/user/home.jsp")
    }

    String getUserUrl() {
        return currentUser?.url
    }

    String getUsername() {
        return currentUser?.username
    }


}