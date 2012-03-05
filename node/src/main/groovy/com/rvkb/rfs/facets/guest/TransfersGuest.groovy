package com.rvkb.rfs.facets.guest

import com.rvkb.rfs.facets.FacetCategory
import net.sourceforge.jfacets.IInstanceFacet
import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.facets.admin.Transfers

@FacetKey(name="transfers", profileId="guest")
@Mixin(FacetCategory)
class TransfersGuest extends Transfers implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        checkLocalhost()
    }

}
