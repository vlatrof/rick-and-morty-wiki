package com.vlatrof.rickandmorty.data.feature.repository

import com.vlatrof.rickandmorty.data.base.BaseRepositoryImpl
import com.vlatrof.rickandmorty.data.feature.datasource.local.EpisodeLocalDataSource
import com.vlatrof.rickandmorty.data.feature.datasource.remote.EpisodeRemoteDataSource
import com.vlatrof.rickandmorty.data.feature.model.episode.EpisodeDataFilter
import com.vlatrof.rickandmorty.data.feature.model.episode.EpisodeDataFilterMapper
import com.vlatrof.rickandmorty.data.feature.model.episode.EpisodeEntity
import com.vlatrof.rickandmorty.data.feature.model.episode.EpisodeResponseDTO
import com.vlatrof.rickandmorty.domain.model.episode.DomainEpisode
import com.vlatrof.rickandmorty.domain.model.episode.EpisodeDomainFilter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeRepositoryImpl @Inject constructor(
    remoteDataSource: EpisodeRemoteDataSource,
    localDataSource: EpisodeLocalDataSource,
    dataFilterMapper: EpisodeDataFilterMapper

) : BaseRepositoryImpl<DomainEpisode, EpisodeDomainFilter, EpisodeResponseDTO,
    EpisodeEntity, EpisodeDataFilter>(

    remoteDataSource,
    localDataSource,
    dataFilterMapper
)
