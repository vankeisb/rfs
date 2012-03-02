package com.rvkb.rfs.facets.guest

import net.sourceforge.jfacets.annotations.FacetKey
import com.rvkb.rfs.model.Config
import woko.facets.builtin.developer.SaveImpl
import woko.facets.builtin.Home
import net.sourceforge.stripes.action.ActionBeanContext
import com.rvkb.rfs.Rfs
import com.rvkb.rfs.RfsInitListener
import net.sourceforge.stripes.action.LocalizableMessage

@FacetKey(name="save", profileId="guest", targetObjectType=Config.class)
class SaveConfigImpl extends SaveImpl {

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