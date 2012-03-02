package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.Config
import woko.facets.builtin.all.RenderTitleImpl
import woko.facets.builtin.RenderTitle

@FacetKey(name="renderTitle", profileId="guest", targetObjectType=Config.class)
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