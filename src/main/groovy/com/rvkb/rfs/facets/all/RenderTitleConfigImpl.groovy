package com.rvkb.rfs.facets.all

import com.rvkb.rfs.model.Config
import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.builtin.RenderTitle
import woko.facets.builtin.all.RenderTitleImpl
import net.sourceforge.jfacets.annotations.FacetKeyList

@FacetKeyList(keys=[
    @FacetKey(name="renderTitle", profileId="guest", targetObjectType=Config.class),
    @FacetKey(name="renderTitle", profileId="admin", targetObjectType=Config.class)
])
class RenderTitleConfigImpl extends RenderTitleImpl implements RenderTitle {

    @Override
    String getTitle() {
        return "Configuration"
    }

    @Override
    String getPath() {
        "/WEB-INF/jsp/guest/renderTitleConfig.jsp"
    }

}