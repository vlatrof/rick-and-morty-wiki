package com.vlatrof.rickandmorty.data.feature.datasource.local

import com.vlatrof.rickandmorty.data.base.BaseLocalDataSource
import com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao.LocationDao
import com.vlatrof.rickandmorty.data.feature.model.location.LocationDataFilter
import com.vlatrof.rickandmorty.data.feature.model.location.LocationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationLocalDataSource @Inject constructor(
    private val locationDao: LocationDao
) : BaseLocalDataSource<LocationEntity, LocationDataFilter>(
    locationDao
) {

    override suspend fun getListByIds(ids: List<Int>) = locationDao.getListByIds(ids)

    override suspend fun getCount(filter: LocationDataFilter) =
        locationDao.getCount(
            name = filter.name,
            type = filter.type,
            dimension = filter.dimension
        )

    override suspend fun getPage(pageSize: Int, pageNumber: Int, filter: LocationDataFilter) =
        locationDao.getPage(
            limit = pageSize,
            offset = pageSize * (pageNumber - 1),
            name = filter.name,
            type = filter.type,
            dimension = filter.dimension
        )
}
