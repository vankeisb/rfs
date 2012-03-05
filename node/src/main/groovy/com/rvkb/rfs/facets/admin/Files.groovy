package com.rvkb.rfs.facets.admin

import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.BaseResolutionFacet
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.ActionBeanContext
import com.rvkb.rfs.facets.FacetCategory
import org.json.JSONObject
import org.json.JSONArray
import woko.facets.WokoFacetContext
import javax.servlet.http.HttpServletRequest
import woko.facets.builtin.RenderObjectJson
import net.sourceforge.stripes.action.StreamingResolution
import org.json.JSONException

@FacetKey(name="files", profileId="admin")
@Mixin(FacetCategory)
class Files extends BaseResolutionFacet {

    Resolution getResolution(ActionBeanContext abc) {
        try {
            def files = store.files
            JSONObject r = new JSONObject();
            JSONArray items = new JSONArray();
            final WokoFacetContext facetContext = getFacetContext();
            final HttpServletRequest request = abc.getRequest();
            for (def f : files) {
                RenderObjectJson roj =
                    (RenderObjectJson)facetContext.getWoko().getFacet(RenderObjectJson.FACET_NAME, request, f);
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
