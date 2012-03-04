package com.rvkb.rfs.facets.buddy

import com.rvkb.rfs.facets.FacetCategory
import com.rvkb.rfs.facets.admin.DownloadAdmin
import net.sourceforge.jfacets.annotations.FacetKey
import net.sourceforge.jfacets.IInstanceFacet

@FacetKey(name="download", profileId="buddy", targetObjectType=com.rvkb.rfs.model.File.class)
@Mixin(FacetCategory)
class DownloadBuddy extends DownloadAdmin implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        return currentUser!=null // needed because of clash with guest facet
    }
}
