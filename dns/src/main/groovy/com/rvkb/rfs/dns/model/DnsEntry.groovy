package com.rvkb.rfs.dns.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.validation.constraints.NotNull

@Entity
class DnsEntry {

    @Id
    @GeneratedValue
    Long id

    @NotNull
    String username

    @NotNull
    String url

}
