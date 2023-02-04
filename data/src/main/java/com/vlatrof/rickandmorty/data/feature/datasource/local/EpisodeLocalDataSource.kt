package com.vlatrof.rickandmorty.data.feature.datasource.local

import com.vlatrof.rickandmorty.data.base.BaseLocalDataSource
import com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao.EpisodeDao
import com.vlatrof.rickandmorty.data.feature.model.episode.EpisodeDataFilter
import com.vlatrof.rickandmorty.data.feature.model.episode.EpisodeEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeLocalDataSource @Inject constructor(
    private val episodeDao: EpisodeDao
) : BaseLocalDataSource<EpisodeEntity, EpisodeDataFilter>(
    episodeDao
) {

    override suspend fun getListByIds(ids: List<Int>) = episodeDao.getListByIds(ids)

    override suspend fun getCount(filter: EpisodeDataFilter) =
        episodeDao.getCount(name = filter.name, code = filter.code)

    override suspend fun getPage(pageSize: Int, pageNumber: Int, filter: EpisodeDataFilter) =
        episodeDao.getPage(
            limit = pageSize,
            offset = pageSize * (pageNumber - 1),
            name = filter.name,
            code = filter.code
        )
}
