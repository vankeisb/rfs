package com.rvkb.rfs.dns

import woko.ri.RiWokoInitListener
import woko.users.UserManager
import woko.persistence.ObjectStore
import com.rvkb.rfs.dns.model.DnsEntry

class DnsInitListener extends RiWokoInitListener {

    private def store

    @Override
    protected ObjectStore createObjectStore() {
        this.store = super.createObjectStore()
    }

    @Override
    protected UserManager createUserManager() {
        return new DnsUserManager(store).createDefaultUsers()
    }


}
