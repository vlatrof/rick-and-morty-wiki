package com.vlatrof.rickandmorty.data.feature.model.character

import com.vlatrof.rickandmorty.data.base.model.BaseDataFilter

data class CharacterDataFilter(
    val name: String,
    val species: String,
    val type: String,
    val gender: String,
    val status: String

) : BaseDataFilter
