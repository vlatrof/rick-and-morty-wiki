package com.vlatrof.rickandmorty.domain.model.location

import com.vlatrof.rickandmorty.domain.model.base.BaseDomainItem

data class DomainLocation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentIds: List<Int>
) : BaseDomainItem
