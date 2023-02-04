package com.vlatrof.rickandmorty.presentation.model.character

import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter

data class PresentationCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originLocationName: String,
    val originLocationId: Int?,
    val lastLocationName: String,
    val lastLocationId: Int?,
    val imageUrl: String,
    val episodeIds: List<Int>
) {
    companion object {

        fun fromDomain(domainCharacter: DomainCharacter): PresentationCharacter {
            with(domainCharacter) {
                return PresentationCharacter(
                    id = id,
                    name = name,
                    status = status,
                    species = species,
                    gender = gender,
                    type = type,
                    originLocationName = originLocationName,
                    originLocationId = originLocationId,
                    lastLocationName = lastLocationName,
                    lastLocationId = lastLocationId,
                    imageUrl = imageUrl,
                    episodeIds = episodeIds
                )
            }
        }
    }
}
