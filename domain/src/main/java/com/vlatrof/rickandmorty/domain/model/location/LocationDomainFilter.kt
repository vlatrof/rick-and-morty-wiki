package com.vlatrof.rickandmorty.domain.model.location

import com.vlatrof.rickandmorty.domain.model.base.BaseDomainFilter

data class LocationDomainFilter(
    val name: String,
    val type: String,
    val dimension: String
) : BaseDomainFilter
