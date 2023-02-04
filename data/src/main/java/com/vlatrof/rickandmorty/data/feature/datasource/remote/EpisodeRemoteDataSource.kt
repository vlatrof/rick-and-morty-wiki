package com.vlatrof.rickandmorty.data.feature.datasource.remote

import com.vlatrof.rickandmorty.data.base.BaseRemoteDataSource
import com.vlatrof.rickandmorty.data.feature.datasource.remote.retrofit.EpisodeApi
import com.vlatrof.rickandmorty.data.feature.model.episode.EpisodeDataFilter
import com.vlatrof.rickandmorty.data.feature.model.episode.EpisodeResponseDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeRemoteDataSource @Inject constructor(
    private val episodeApi: EpisodeApi
) : BaseRemoteDataSource<EpisodeResponseDTO, EpisodeDataFilter>() {

    override suspend fun fetchById(id: Int) = episodeApi.getById(id)

    override suspend fun fetchListByIds(ids: List<Int>) =
        episodeApi.getListByIds(ids.joinToString(","))

    override suspend fun fetchPage(pageNumber: Int, filter: EpisodeDataFilter) =
        episodeApi.getPage(
            page = pageNumber,
            name = filter.name,
            episode = filter.code
        )
}
