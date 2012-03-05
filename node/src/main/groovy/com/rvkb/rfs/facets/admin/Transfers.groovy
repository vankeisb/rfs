package com.rvkb.rfs.facets.admin

import net.sourceforge.jfacets.annotations.FacetKey
import net.sourceforge.stripes.action.ActionBeanContext
import com.rvkb.rfs.facets.FacetCategory
import com.rvkb.rfs.model.FileTransfer
import woko.facets.BaseResolutionFacet
import net.sourceforge.stripes.action.Resolution
import org.json.JSONObject
import org.json.JSONArray
import woko.facets.WokoFacetContext
import javax.servlet.http.HttpServletRequest
import woko.facets.builtin.RenderObjectJson
import net.sourceforge.stripes.action.StreamingResolution
import org.json.JSONException

@FacetKey(name="transfers", profileId="admin")
@Mixin(FacetCategory)
class Transfers extends BaseResolutionFacet {

    Resolution getResolution(ActionBeanContext abc) {
        try {
            List<FileTransfer> lastTransfers = store.lastDownloads;
            JSONObject r = new JSONObject();
            JSONArray items = new JSONArray();
            final WokoFacetContext facetContext = getFacetContext();
            final HttpServletRequest request = abc.getRequest();
            for (FileTransfer ft : lastTransfers) {
                RenderObjectJson roj =
                    (RenderObjectJson)facetContext.getWoko().getFacet(RenderObjectJson.FACET_NAME, request, ft);
                JSONObject jo = roj.objectToJson(request);
                items.put(jo);
            }
            r.put("items", items);
            return new StreamingResolution("text/json", r.toString());
        } catch(JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
