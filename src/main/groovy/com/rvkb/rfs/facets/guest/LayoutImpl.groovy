package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.all.LayoutAll
import woko.facets.builtin.Layout

@FacetKey(name="layout", profileId="guest")
class LayoutImpl extends LayoutAll {

    @Override
    String getAppTitle() {
        "Rfs"
    }

}