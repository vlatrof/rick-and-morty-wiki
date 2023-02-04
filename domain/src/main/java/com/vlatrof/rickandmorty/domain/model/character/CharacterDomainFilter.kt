package com.vlatrof.rickandmorty.domain.model.character

import com.vlatrof.rickandmorty.domain.model.base.BaseDomainFilter

data class CharacterDomainFilter(
    val name: String,
    val species: String,
    val type: String,
    val gender: String,
    val status: String
) : BaseDomainFilter
