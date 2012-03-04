package com.rvkb.rfs.facets.all

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.User
import woko.facets.builtin.all.RenderTitleImpl
import woko.facets.builtin.RenderTitle
import com.rvkb.rfs.facets.FacetCategory

@FacetKey(name="renderTitle", profileId="all", targetObjectType=User.class)
@Mixin(FacetCategory)
class RenderTitleUserImpl extends RenderTitleImpl implements RenderTitle {

    @Override
    String getTitle() {
        transientUser ? "New buddy" : targetObject.username
    }

    boolean isTransientUser() {
        targetObject?.id == null
    }

    @Override
    String getPath() {
        "/WEB-INF/jsp/all/renderTitleUser.jsp"
    }

}