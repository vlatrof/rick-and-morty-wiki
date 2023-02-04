package com.vlatrof.rickandmorty.presentation.model.location

import com.vlatrof.rickandmorty.domain.model.location.DomainLocation

data class PresentationLocation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentIds: List<Int>
) {
    companion object {

        fun fromDomain(domainLocation: DomainLocation): PresentationLocation {
            with(domainLocation) {
                return PresentationLocation(
                    id = id,
                    name = name,
                    type = type,
                    dimension = dimension,
                    residentIds = residentIds
                )
            }
        }
    }
}
