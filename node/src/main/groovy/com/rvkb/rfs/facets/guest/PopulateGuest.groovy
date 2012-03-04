package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey

import com.rvkb.rfs.facets.FacetCategory
import net.sourceforge.jfacets.IInstanceFacet
import com.rvkb.rfs.facets.admin.PopulateImpl

@FacetKey(name="populate", profileId="all")
@Mixin(FacetCategory)
class PopulateGuest extends PopulateImpl implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        checkLocalhost()
    }

}