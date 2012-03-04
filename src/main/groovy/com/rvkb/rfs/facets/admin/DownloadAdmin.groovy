package com.rvkb.rfs.facets.admin

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.File
import woko.facets.BaseResolutionFacet
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.ActionBeanContext
import com.rvkb.rfs.facets.FacetCategory
import net.sourceforge.stripes.action.StreamingResolution
import net.sourceforge.stripes.action.ErrorResolution
import com.rvkb.rfs.util.DownloadResolution
import com.rvkb.rfs.model.FileTransfer

@FacetKey(name="download", profileId="admin", targetObjectType=File.class)
@Mixin(FacetCategory)
class DownloadAdmin extends BaseResolutionFacet {

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
        def localFile = new java.io.File(fullPath)
        if (!localFile.exists()) {
            // TODO cleanup ? file should not exist in the db...
            return new ErrorResolution(404)
        }
        FileTransfer ft = null
        return new DownloadResolution(localFile).
            onStart {
                ft = new FileTransfer(download: false, buddy: currentUser, startedOn: new Date(), relativePath: f.path)
                store.save(ft)
            }.
            onFinish {
                ft.finishedOn = new Date()
                store.save(ft)
            }.
            onError { e ->
                ft.finishedOn = new Date()
                store.save(ft)
                println "ERROR !"
                e.printStackTrace()
            }
    }


}