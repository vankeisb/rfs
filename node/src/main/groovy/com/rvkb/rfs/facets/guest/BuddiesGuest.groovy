package com.rvkb.rfs.facets.guest

import com.rvkb.rfs.facets.FacetCategory
import net.sourceforge.jfacets.IInstanceFacet
import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.facets.admin.Buddies

@FacetKey(name="buddies", profileId="guest")
@Mixin(FacetCategory)
class BuddiesGuest extends Buddies implements IInstanceFacet {

    boolean matchesTargetObject(Object targetObject) {
        checkLocalhost()
    }

}
