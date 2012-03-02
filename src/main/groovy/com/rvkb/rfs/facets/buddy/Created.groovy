package com.rvkb.rfs.facets.buddy

import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.BaseResolutionFacet
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.ActionBeanContext
import com.rvkb.rfs.facets.FacetCategory
import com.rvkb.rfs.model.User
import net.sourceforge.stripes.action.ErrorResolution
import net.sourceforge.stripes.validation.Validate
import net.sourceforge.stripes.action.StreamingResolution

@FacetKey(name="created", profileId="buddy")
@Mixin(FacetCategory)
class Created extends BaseResolutionFacet {

    @Validate(required=true)
    String path

    @Validate(required=true)
    String user

    Resolution getResolution(ActionBeanContext abc) {
        // lookup for the buddy
        User u = store.getBuddy(user)
        if (!u) {
            return new ErrorResolution(404)
        }
        // look for the file locally
        def f = store.loadFileByRelativePath(path)
        if (!f) {
            // go grab the file !
            rfs.downloadManager.download(u, path)
            return new StreamingResolution("text/plain", "I'm coming to get this file ...")
        } else {
            return new StreamingResolution("text/plain", "I've got this file already, I won't bother you ...")
        }
    }


}