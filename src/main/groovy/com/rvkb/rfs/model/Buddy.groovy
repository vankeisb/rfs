package com.rvkb.rfs.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue

@Entity
class Buddy {

    @Id
    @GeneratedValue
    Long id

    String name

    String url

    String username

    String password

}
