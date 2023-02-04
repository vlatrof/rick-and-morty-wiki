package com.vlatrof.rickandmorty.presentation.model.episode

import com.vlatrof.rickandmorty.domain.model.episode.DomainEpisode

data class PresentationEpisode(
    val id: Int,
    val name: String,
    val code: String,
    val airDate: String,
    val characterIds: List<Int>
) {
    companion object {

        fun fromDomain(domainEpisode: DomainEpisode): PresentationEpisode {
            with(domainEpisode) {
                return PresentationEpisode(
                    id = id,
                    name = name,
                    code = code,
                    airDate = airDate,
                    characterIds = characterIds
                )
            }
        }
    }
}
