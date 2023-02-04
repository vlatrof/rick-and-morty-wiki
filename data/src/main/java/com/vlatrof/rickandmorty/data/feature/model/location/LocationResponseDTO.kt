package com.vlatrof.rickandmorty.data.feature.model.location

import com.vlatrof.rickandmorty.data.base.model.BaseResponseDTO
import com.vlatrof.rickandmorty.data.util.ResponseUtils.IF_BLANK_PLACEHOLDER
import com.vlatrof.rickandmorty.data.util.ResponseUtils.urlToId
import com.vlatrof.rickandmorty.domain.model.location.DomainLocation

data class LocationResponseDTO(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>

) : BaseResponseDTO {

    override fun toDomain() = DomainLocation(
        id = id,
        name = name,
        type = type.ifBlank { IF_BLANK_PLACEHOLDER },
        dimension = dimension.ifBlank { IF_BLANK_PLACEHOLDER },
        residentIds = residents.map { urlToId(it)!! }
    )

    override fun toEntity() = LocationEntity(
        id = id,
        name = name,
        type = type.ifBlank { IF_BLANK_PLACEHOLDER },
        dimension = dimension.ifBlank { IF_BLANK_PLACEHOLDER },
        residentIds = residents.map { urlToId(it)!! }
    )
}
