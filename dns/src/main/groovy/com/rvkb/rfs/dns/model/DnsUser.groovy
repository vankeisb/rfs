package com.rvkb.rfs.dns.model

import javax.persistence.Entity
import woko.ext.usermanagement.hibernate.HbUser

@Entity
class DnsUser extends HbUser {

    String host
    String port
    Date lastUpdated

}
