package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.File
import woko.facets.BaseResolutionFacet
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.ActionBeanContext
import com.rvkb.rfs.facets.FacetCategory
import net.sourceforge.stripes.action.StreamingResolution
import net.sourceforge.stripes.action.ErrorResolution

@FacetKey(name="download", profileId="guest", targetObjectType=File.class)
@Mixin(FacetCategory)
class DownloadFileImpl extends BaseResolutionFacet {

    Resolution getResolution(ActionBeanContext abc) {
        def fullPath = rfsConfig.baseDir
        File f = targetObject
        if (f==null) {
            return new ErrorResolution(404)
        }
        def path = f.path
        if (!path.startsWith("/")) {
            fullPath += "/"
        }
        fullPath += path
        String fileName = path
        int i = fileName.lastIndexOf("/")
        if (i!=-1) {
            fileName = path[i+1..-1]
        }
        def localFile = new java.io.File(fullPath)
        if (!localFile.exists()) {
            // TODO cleanup ? file should not exist in the db...
            return new ErrorResolution(404)
        }
        return new StreamingResolution("rfsfile", new FileInputStream(fullPath)).
          setFilename(fileName).
          setRangeSupport(true)
    }


}