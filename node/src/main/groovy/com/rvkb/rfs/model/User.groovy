package com.rvkb.rfs.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.validation.constraints.NotNull
import woko.ext.usermanagement.hibernate.HbUser

@Entity
class User extends HbUser {

    @NotNull
    String url

    boolean buddy = false

    @Override
    void setRoles(List<String> roles) {
        super.setRoles(roles)
        buddy = roles?.contains("buddy")
    }


}
