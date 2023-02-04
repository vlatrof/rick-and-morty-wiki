package com.vlatrof.rickandmorty.data.feature.model.episode

import com.vlatrof.rickandmorty.data.base.model.BaseDataFilterMapper
import com.vlatrof.rickandmorty.domain.model.episode.EpisodeDomainFilter
import javax.inject.Inject

class EpisodeDataFilterMapper @Inject constructor() :
    BaseDataFilterMapper<EpisodeDomainFilter, EpisodeDataFilter>() {

    override fun domainToData(domainFilter: EpisodeDomainFilter) = EpisodeDataFilter(
        name = domainFilter.name,
        code = domainFilter.code
    )
}
