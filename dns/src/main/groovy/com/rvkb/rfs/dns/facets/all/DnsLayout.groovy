package com.rvkb.rfs.dns.facets.all

import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.all.LayoutAll

@FacetKey(name="layout", profileId="all")
class DnsLayout extends LayoutAll {

    @Override
    String getAppTitle() {
        "RFS Name Server"
    }


}