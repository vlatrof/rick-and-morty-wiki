package com.vlatrof.rickandmorty.domain.model.character

import com.vlatrof.rickandmorty.domain.model.base.BaseDomainItem

data class DomainCharacter(
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
) : BaseDomainItem
