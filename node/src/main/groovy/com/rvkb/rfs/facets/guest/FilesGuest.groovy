package com.rvkb.rfs.facets.guest

import com.rvkb.rfs.facets.FacetCategory
import net.sourceforge.jfacets.IInstanceFacet
import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.facets.admin.Files

@FacetKey(name="files", profileId="guest")
@Mixin(FacetCategory)
class FilesGuest extends Files implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        checkLocalhost()
    }

}
