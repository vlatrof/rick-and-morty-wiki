package com.vlatrof.rickandmorty.domain.model.episode

import com.vlatrof.rickandmorty.domain.model.base.BaseDomainItem

data class DomainEpisode(
    val id: Int,
    val name: String,
    val code: String,
    val airDate: String,
    val characterIds: List<Int>
) : BaseDomainItem
