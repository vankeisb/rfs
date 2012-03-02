package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.Buddy
import woko.facets.builtin.developer.SaveImpl
import woko.facets.builtin.Save

@FacetKey(name="save", profileId="guest", targetObjectType=Buddy.class)
class SaveBuddyImpl extends SaveImpl implements Save {

    @Override
    protected String getTargetFacetAfterSave() {
        return "home"
    }


}