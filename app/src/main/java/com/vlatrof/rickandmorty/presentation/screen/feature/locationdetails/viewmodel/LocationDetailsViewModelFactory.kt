package com.vlatrof.rickandmorty.presentation.screen.feature.locationdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlatrof.rickandmorty.domain.model.character.CharacterDomainFilter
import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter
import com.vlatrof.rickandmorty.domain.model.location.DomainLocation
import com.vlatrof.rickandmorty.domain.model.location.LocationDomainFilter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class LocationDetailsViewModelFactory @AssistedInject constructor(
    @Assisted private val locationId: Int,
    private val locationRepository: BaseRepository<DomainLocation, LocationDomainFilter>,
    private val characterRepository: BaseRepository<DomainCharacter, CharacterDomainFilter>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == LocationDetailsViewModel::class.java)
        return LocationDetailsViewModel(
            locationId,
            locationRepository,
            characterRepository
        ) as T
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted locationId: Int): LocationDetailsViewModelFactory
    }
}
