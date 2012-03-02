package com.rvkb.rfs.facets.admin

import com.rvkb.rfs.model.User
import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.developer.SaveImpl

@FacetKey(name="save", profileId="admin", targetObjectType=User.class)
class SaveBuddyAdmin extends SaveImpl {

    @Override
    protected String getTargetFacetAfterSave() {
        return "home"
    }


}