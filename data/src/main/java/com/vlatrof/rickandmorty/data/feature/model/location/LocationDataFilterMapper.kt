package com.vlatrof.rickandmorty.data.feature.model.location

import com.vlatrof.rickandmorty.data.base.model.BaseDataFilterMapper
import com.vlatrof.rickandmorty.domain.model.location.LocationDomainFilter
import javax.inject.Inject

class LocationDataFilterMapper @Inject constructor() :
    BaseDataFilterMapper<LocationDomainFilter, LocationDataFilter>() {

    override fun domainToData(domainFilter: LocationDomainFilter) = LocationDataFilter(
        name = domainFilter.name,
        type = domainFilter.type,
        dimension = domainFilter.dimension
    )
}
