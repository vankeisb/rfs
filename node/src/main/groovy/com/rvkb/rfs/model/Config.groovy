package com.rvkb.rfs.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
class Config {

    @Id
    Long id = 1

    String baseDir

    String username

    String password

    String dns

    String port

}
