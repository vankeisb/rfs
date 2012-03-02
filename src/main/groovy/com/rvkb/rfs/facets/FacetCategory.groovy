package com.rvkb.rfs.facets

import woko.facets.BaseFacet
import com.rvkb.rfs.RfsStore
import com.rvkb.rfs.model.Config

@Category(BaseFacet.class)
class FacetCategory {

    RfsStore getStore() {
        return (RfsStore)facetContext.woko.objectStore
    }

    Config getRfsConfig() {
        return store.config
    }

    def getTargetObject() {
        return facetContext.targetObject
    }

}
