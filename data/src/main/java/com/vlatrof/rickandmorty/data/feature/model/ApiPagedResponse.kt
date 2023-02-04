package com.vlatrof.rickandmorty.data.feature.model

data class ApiPagedResponse<T>(
    val info: PageInfo,
    val results: List<T>
) {
    data class PageInfo(
        val count: Int,
        val pages: Int,
        val next: String,
        val prev: String
    )
}
