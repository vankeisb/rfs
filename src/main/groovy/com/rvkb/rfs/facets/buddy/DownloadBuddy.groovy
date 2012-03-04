package com.rvkb.rfs.facets.buddy

import com.rvkb.rfs.facets.FacetCategory
import com.rvkb.rfs.facets.admin.DownloadAdmin
import net.sourceforge.jfacets.annotations.FacetKey

@FacetKey(name="download", profileId="buddy", targetObjectType=com.rvkb.rfs.model.File.class)
@Mixin(FacetCategory)
class DownloadBuddy extends DownloadAdmin {

}
