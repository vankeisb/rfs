package com.rvkb.rfs.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.validation.constraints.NotNull
import javax.persistence.ManyToOne
import javax.persistence.FetchType

@Entity
class Download {

    @Id
    @GeneratedValue
    Long id

    @NotNull
    String relativePath

    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    User buddy

    Date startedOn

    Date finishedOn

}
