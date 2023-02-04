package com.vlatrof.rickandmorty.data.feature.model.episode

import com.vlatrof.rickandmorty.data.base.model.BaseDataFilter

data class EpisodeDataFilter(
    val name: String,
    val code: String

) : BaseDataFilter
