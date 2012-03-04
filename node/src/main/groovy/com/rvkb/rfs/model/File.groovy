package com.rvkb.rfs.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class File {

    @Id
    @GeneratedValue
    Long id

    String path

}
