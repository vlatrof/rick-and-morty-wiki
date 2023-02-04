package com.vlatrof.rickandmorty.presentation.screen.feature.locationlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlatrof.rickandmorty.domain.model.location.DomainLocation
import com.vlatrof.rickandmorty.domain.model.location.LocationDomainFilter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationListViewModelFactory @Inject constructor(
    private val locationRepository: BaseRepository<DomainLocation, LocationDomainFilter>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationListViewModel(
            locationRepository = locationRepository
        ) as T
    }
}
