package com.vlatrof.rickandmorty.presentation.model.character

import com.vlatrof.rickandmorty.domain.model.character.CharacterDomainFilter
import com.vlatrof.rickandmorty.presentation.common.extension.EMPTY

data class PresentationCharacterFilter(
    val name: String = String.EMPTY,
    val species: String = String.EMPTY,
    val type: String = String.EMPTY,
    val gender: String = String.EMPTY,
    val status: String = String.EMPTY
) {

    fun toDomain() = CharacterDomainFilter(
        name = name,
        species = species,
        type = type,
        gender = gender,
        status = status
    )

    companion object {
        val default get() = PresentationCharacterFilter()
    }
}
