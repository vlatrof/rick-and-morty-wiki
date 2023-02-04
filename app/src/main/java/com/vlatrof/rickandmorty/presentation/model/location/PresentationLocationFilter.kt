package com.vlatrof.rickandmorty.presentation.model.location

import com.vlatrof.rickandmorty.domain.model.location.LocationDomainFilter
import com.vlatrof.rickandmorty.presentation.common.extension.EMPTY

data class PresentationLocationFilter(
    val name: String = String.EMPTY,
    val type: String = String.EMPTY,
    val dimension: String = String.EMPTY
) {

    fun toDomain() = LocationDomainFilter(
        name = name,
        type = type,
        dimension = dimension
    )

    companion object {
        val default get() = PresentationLocationFilter()
    }
}
