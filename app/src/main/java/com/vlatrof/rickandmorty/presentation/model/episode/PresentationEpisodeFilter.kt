package com.vlatrof.rickandmorty.presentation.model.episode

import com.vlatrof.rickandmorty.domain.model.episode.EpisodeDomainFilter
import com.vlatrof.rickandmorty.presentation.common.extension.EMPTY

data class PresentationEpisodeFilter(
    val name: String = String.EMPTY,
    val code: String = String.EMPTY
) {

    fun toDomain() = EpisodeDomainFilter(name, code)

    companion object {
        val default get() = PresentationEpisodeFilter()
    }
}
