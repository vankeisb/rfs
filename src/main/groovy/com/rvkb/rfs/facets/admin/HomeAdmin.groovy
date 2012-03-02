package com.rvkb.rfs.facets.admin

import com.rvkb.rfs.facets.FacetCategory
import com.rvkb.rfs.model.Config
import com.rvkb.rfs.model.User
import net.sourceforge.jfacets.annotations.FacetKey
import net.sourceforge.stripes.action.ActionBeanContext
import net.sourceforge.stripes.action.ForwardResolution
import net.sourceforge.stripes.action.RedirectResolution
import net.sourceforge.stripes.action.Resolution
import woko.facets.builtin.all.HomeImpl
import com.rvkb.rfs.model.Download

@FacetKey(name="home", profileId="admin")
@Mixin(FacetCategory)
class HomeAdmin extends HomeImpl {

    @Override
    Resolution getResolution(ActionBeanContext abc) {
        // try to get the config
        def c = store.config
        if (c?.baseDir) {
            // forward to admin interface
            return new ForwardResolution("/WEB-INF/jsp/guest/home.jsp")
        } else {
            // redirect to edit config page
            return new RedirectResolution("/edit/Config/1")
        }
    }

    List<com.rvkb.rfs.model.File> getFiles() {
        return store.files
    }

    Config getConfig() {
        return store.config
    }

    List<User> getBuddies() {
        return store.buddies
    }

    List<Download> getLastDownloads() {
        return store.lastDownloads
    }

}
