package com.vlatrof.rickandmorty.data.feature.datasource.remote

import com.vlatrof.rickandmorty.data.base.BaseRemoteDataSource
import com.vlatrof.rickandmorty.data.feature.datasource.remote.retrofit.LocationApi
import com.vlatrof.rickandmorty.data.feature.model.location.LocationDataFilter
import com.vlatrof.rickandmorty.data.feature.model.location.LocationResponseDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRemoteDataSource @Inject constructor(
    private val locationApi: LocationApi
) : BaseRemoteDataSource<LocationResponseDTO, LocationDataFilter>() {

    override suspend fun fetchById(id: Int) = locationApi.getById(id)

    override suspend fun fetchListByIds(ids: List<Int>) =
        locationApi.getListByIds(ids.joinToString(","))

    override suspend fun fetchPage(pageNumber: Int, filter: LocationDataFilter) =
        locationApi.getPage(
            page = pageNumber,
            name = filter.name,
            type = filter.type,
            dimension = filter.dimension
        )
}
