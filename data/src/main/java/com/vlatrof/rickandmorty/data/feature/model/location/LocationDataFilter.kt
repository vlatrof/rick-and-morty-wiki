package com.vlatrof.rickandmorty.data.feature.model.location

import com.vlatrof.rickandmorty.data.base.model.BaseDataFilter

data class LocationDataFilter(
    val name: String,
    val type: String,
    val dimension: String

) : BaseDataFilter
