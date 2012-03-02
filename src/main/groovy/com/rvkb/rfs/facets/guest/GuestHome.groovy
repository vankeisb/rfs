package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.Home
import woko.facets.builtin.all.HomeImpl
import net.sourceforge.stripes.action.RedirectResolution
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.ActionBeanContext
import net.sourceforge.stripes.action.ForwardResolution
import com.rvkb.rfs.facets.FacetCategory
import com.rvkb.rfs.model.Config
import com.rvkb.rfs.model.Buddy

@FacetKey(name=Home.FACET_NAME, profileId="guest")
@Mixin(FacetCategory)
class GuestHome extends HomeImpl {

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

    List<File> getFiles() {
        return store.files
    }

    Config getConfig() {
        return store.config
    }

    List<Buddy> getBuddies() {
        return store.buddies
    }

}
