package com.rvkb.rfs.dns.actions

import net.sourceforge.stripes.action.UrlBinding
import net.sourceforge.stripes.action.ActionBean
import net.sourceforge.stripes.action.ActionBeanContext
import net.sourceforge.stripes.action.Resolution
import net.sourceforge.stripes.action.DefaultHandler
import com.rvkb.rfs.dns.model.DnsUser
import woko.hibernate.HibernateStore
import woko.Woko
import org.hibernate.criterion.Restrictions
import net.sourceforge.stripes.validation.Validate
import net.sourceforge.stripes.action.StreamingResolution

@UrlBinding("/url/{username}")
class RfsDnsActionBean implements ActionBean {

    ActionBeanContext context

    @Validate(required=true)
    String username

    @DefaultHandler
    Resolution display() {
        HibernateStore store = Woko.getWoko(context.servletContext).objectStore
        DnsUser u = store.session.createCriteria(DnsUser.class).
            add(Restrictions.eq("username", username)).
            uniqueResult()
        String text
        if (u) {
            text = "http://$u.host:$u.port"
        } else {
            text = "No such user"
            context.response.setStatus(404)
        }
        return new StreamingResolution("text/plain", text)
    }

}
