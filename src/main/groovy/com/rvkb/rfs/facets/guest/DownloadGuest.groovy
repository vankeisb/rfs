package com.rvkb.rfs.facets.guest

import com.rvkb.rfs.facets.admin.DownloadAdmin
import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.facets.FacetCategory
import net.sourceforge.jfacets.IInstanceFacet

@FacetKey(name="download", profileId="guest", targetObjectType=com.rvkb.rfs.model.File.class)
@Mixin(FacetCategory)
class DownloadGuest extends DownloadAdmin implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        checkLocalhost()
    }

}
