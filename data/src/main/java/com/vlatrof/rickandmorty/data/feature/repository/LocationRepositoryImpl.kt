package com.vlatrof.rickandmorty.data.feature.repository

import com.vlatrof.rickandmorty.data.base.BaseRepositoryImpl
import com.vlatrof.rickandmorty.data.feature.datasource.local.LocationLocalDataSource
import com.vlatrof.rickandmorty.data.feature.datasource.remote.LocationRemoteDataSource
import com.vlatrof.rickandmorty.data.feature.model.location.LocationDataFilter
import com.vlatrof.rickandmorty.data.feature.model.location.LocationDataFilterMapper
import com.vlatrof.rickandmorty.data.feature.model.location.LocationEntity
import com.vlatrof.rickandmorty.data.feature.model.location.LocationResponseDTO
import com.vlatrof.rickandmorty.domain.model.location.DomainLocation
import com.vlatrof.rickandmorty.domain.model.location.LocationDomainFilter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepositoryImpl @Inject constructor(
    remoteDataSource: LocationRemoteDataSource,
    localDataSource: LocationLocalDataSource,
    dataFilterMapper: LocationDataFilterMapper

) : BaseRepositoryImpl<DomainLocation, LocationDomainFilter, LocationResponseDTO,
    LocationEntity, LocationDataFilter>(

    remoteDataSource,
    localDataSource,
    dataFilterMapper
)
