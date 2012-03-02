package com.rvkb.rfs.facets.admin

import com.rvkb.rfs.Rfs
import com.rvkb.rfs.RfsInitListener
import com.rvkb.rfs.model.Config
import net.sourceforge.jfacets.annotations.FacetKey
import net.sourceforge.stripes.action.ActionBeanContext
import net.sourceforge.stripes.action.LocalizableMessage
import woko.facets.builtin.Home
import woko.facets.builtin.developer.SaveImpl

@FacetKey(name="save", profileId="admin", targetObjectType=Config.class)
class SaveConfigAdmin extends SaveImpl {

    @Override
    protected void doSave(ActionBeanContext abc) {
        super.doSave(abc)
        // reinit if path has changed
        Rfs rfs = RfsInitListener.getRfs(abc.servletContext)
        Config c = facetContext.targetObject
        if (rfs.baseDir!=c.baseDir) {
            File newBaseDir = new File(c.baseDir)
            if (!newBaseDir.exists()) {
                newBaseDir.mkdirs()
            }
            rfs.initialize(newBaseDir)
        }
        abc.messages.clear()
        abc.messages.add(new LocalizableMessage("application.config.saved"))
    }

    @Override
    protected String getTargetFacetAfterSave() {
        Home.FACET_NAME
    }


}