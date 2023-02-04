package com.vlatrof.rickandmorty.data.feature.model.character

import com.vlatrof.rickandmorty.data.base.model.BaseResponseDTO
import com.vlatrof.rickandmorty.data.util.ResponseUtils.IF_BLANK_PLACEHOLDER
import com.vlatrof.rickandmorty.data.util.ResponseUtils.urlToId
import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter

data class CharacterResponseDTO(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>

) : BaseResponseDTO {

    data class Origin(
        val name: String,
        val url: String
    )

    data class Location(
        val name: String,
        val url: String
    )

    override fun toDomain() = DomainCharacter(
        id = id,
        name = name,
        status = status,
        species = species.ifBlank { IF_BLANK_PLACEHOLDER },
        gender = gender,
        type = type.ifBlank { IF_BLANK_PLACEHOLDER },
        originLocationName = origin.name,
        originLocationId = urlToId(origin.url),
        lastLocationName = location.name,
        lastLocationId = urlToId(location.url),
        imageUrl = image,
        episodeIds = episode.map { urlToId(it)!! }
    )

    override fun toEntity() = CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species.ifBlank { IF_BLANK_PLACEHOLDER },
        gender = gender,
        type = type.ifBlank { IF_BLANK_PLACEHOLDER },
        originLocationName = origin.name,
        originLocationId = urlToId(origin.url),
        lastLocationName = location.name,
        lastLocationId = urlToId(location.url),
        imageUrl = image,
        episodeIds = episode.map { urlToId(it)!! }
    )
}
