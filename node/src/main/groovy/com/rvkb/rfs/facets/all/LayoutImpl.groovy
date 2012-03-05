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
    List<String> getJsIncludes() {
        return [
          "/js/dojo-1.6.1/"
        ]
    }


}