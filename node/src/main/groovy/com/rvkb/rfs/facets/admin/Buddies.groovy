package com.rvkb.rfs.facets.admin

import net.sourceforge.jfacets.annotations.FacetKey
import woko.facets.BaseResolutionFacet
import net.sourceforge.stripes.action.ActionBeanContext
import net.sourceforge.stripes.action.Resolution
import com.rvkb.rfs.model.FileTransfer
import com.rvkb.rfs.model.User
import com.rvkb.rfs.facets.FacetCategory
import org.json.JSONObject
import org.json.JSONArray
import woko.facets.WokoFacetContext
import javax.servlet.http.HttpServletRequest
import woko.facets.builtin.RenderObjectJson
import net.sourceforge.stripes.action.StreamingResolution
import org.json.JSONException
import com.rvkb.rfs.util.RfsHttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.HttpResponse
import org.apache.http.util.EntityUtils
import org.apache.http.conn.HttpHostConnectException

@FacetKey(name="buddies", profileId="admin")
@Mixin(FacetCategory)
class Buddies extends BaseResolutionFacet {

    Resolution getResolution(ActionBeanContext abc) {
        RfsHttpClient cli = new RfsHttpClient(new DefaultHttpClient())
        try {
            List<User> buddies = store.getBuddies()
            JSONObject r = new JSONObject();
            JSONArray items = new JSONArray();
            final WokoFacetContext facetContext = getFacetContext();
            final HttpServletRequest request = abc.getRequest();
            def cfg = store.config
            for (User u : buddies) {
                // try to get the URL of the buddy to see if he's online
                boolean online = false
                boolean auth = false
                try {
                    def c = cli.login(u.url, cfg.username, cfg.password)
                    auth = c != null
                    online = true
                } catch(HttpHostConnectException e) {
                    online = false
                }

                RenderObjectJson roj =
                    (RenderObjectJson)facetContext.getWoko().getFacet(RenderObjectJson.FACET_NAME, request, u);
                JSONObject jo = roj.objectToJson(request);
                jo.put("online", online)
                jo.put("auth", auth)
                items.put(jo);
            }
            r.put("items", items);
            return new StreamingResolution("text/json", r.toString());
        } catch(JSONException e) {
            throw new RuntimeException(e);
        } finally {
            cli.close()
        }
    }


}
