package com.rvkb.rfs.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.validation.constraints.NotNull

@Entity
class Buddy {

    @Id
    @GeneratedValue
    Long id

    @NotNull
    String name

    @NotNull
    String url

    @NotNull
    String username

    @NotNull
    String password

}
