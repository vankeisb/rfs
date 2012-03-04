package com.rvkb.rfs.dns

import woko.ext.usermanagement.hibernate.HibernateUserManager
import woko.hibernate.HibernateStore
import woko.ext.usermanagement.core.DatabaseUserManager

class DnsUserManager extends HibernateUserManager {

    DnsUserManager(HibernateStore hibernateStore) {
        super(hibernateStore)
    }

    @Override
    DatabaseUserManager createDefaultUsers() {
        super.createDefaultUsers()
        createUser("kakou", "kakou", ["user"])
        createUser("babz", "babz", ["user"])
        this
    }


}
