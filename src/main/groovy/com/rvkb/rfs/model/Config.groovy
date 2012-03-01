package com.rvkb.rfs.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Config {

    @Id
    Long id = 1

    String baseDir = "/tmp/shared"

}
