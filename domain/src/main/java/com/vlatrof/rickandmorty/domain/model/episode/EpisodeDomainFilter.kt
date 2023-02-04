package com.vlatrof.rickandmorty.domain.model.episode

import com.vlatrof.rickandmorty.domain.model.base.BaseDomainFilter

data class EpisodeDomainFilter(
    val name: String,
    val code: String
) : BaseDomainFilter
