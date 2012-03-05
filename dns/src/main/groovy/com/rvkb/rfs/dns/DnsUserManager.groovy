package com.rvkb.rfs.dns

import woko.ext.usermanagement.hibernate.HibernateUserManager
import woko.hibernate.HibernateStore
import woko.ext.usermanagement.core.DatabaseUserManager
import com.rvkb.rfs.dns.model.DnsUser
import woko.hibernate.TxCallback
import woko.ext.usermanagement.core.User

class DnsUserManager extends HibernateUserManager {

    DnsUserManager(HibernateStore hibernateStore) {
        super(hibernateStore, DnsUser.class)
    }

    DnsUserManager(HibernateStore hibernateStore, Class<? extends User> userClass) {
        super(hibernateStore, userClass)
    }

    @Override
    DatabaseUserManager createDefaultUsers() {
        super.createDefaultUsers()
        def u = createUser("kakou", "kakou", ["user"])
        u.url = "http://localhost:9999/rfs"
        hibernateStore.doInTx({ st, sess ->
            hibernateStore.save(u)
        } as TxCallback)
        u = createUser("babz", "babz", ["user"])
        u.url = "http://localhost:8080/rfs"
        hibernateStore.doInTx({ st, sess ->
            hibernateStore.save(u)
        } as TxCallback)
        this
    }


}
