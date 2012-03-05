package com.rvkb.rfs.facets.all

import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.all.LayoutAll
import woko.facets.builtin.Layout

@FacetKey(name="layout", profileId="all")
class LayoutImpl extends LayoutAll {

    @Override
    String getAppTitle() {
        "RFS"
    }

    @Override
    String getLayoutPath() {
        "/WEB-INF/jsp/all/layout.jsp"
    }


}