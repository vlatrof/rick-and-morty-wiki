package com.vlatrof.rickandmorty.presentation.screen.feature.locationdetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vlatrof.rickandmorty.domain.model.character.CharacterDomainFilter
import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter
import com.vlatrof.rickandmorty.domain.model.location.DomainLocation
import com.vlatrof.rickandmorty.domain.model.location.LocationDomainFilter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import com.vlatrof.rickandmorty.presentation.model.character.PresentationCharacter
import com.vlatrof.rickandmorty.presentation.model.location.PresentationLocation
import com.vlatrof.rickandmorty.presentation.screen.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationDetailsViewModel(
    private val locationId: Int,
    private val locationRepository: BaseRepository<DomainLocation, LocationDomainFilter>,
    private val characterRepository: BaseRepository<DomainCharacter, CharacterDomainFilter>,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val _resourceState =
        MutableLiveData<Pair<PresentationLocation, List<PresentationCharacter>>?>()
    val resourceState: MutableLiveData<Pair<PresentationLocation, List<PresentationCharacter>>?> =
        _resourceState

    init {
        loadResource(locationId)
    }

    fun onRefreshLayoutSwiped() {
        loadResource(locationId)
    }

    private fun loadResource(id: Int) = viewModelScope.launch(ioDispatcher) {
        mutableFetchingState.postValue(true)

        val locationResult = locationRepository.getById(id)
        handleDomainResultRemoteError(locationResult)

        val location = locationResult.data
        if (location != null) {
            val charactersResult = characterRepository.getListByIds(location.residentIds)
            _resourceState.postValue(
                Pair(
                    first = PresentationLocation.fromDomain(location),
                    second = charactersResult.data?.map { PresentationCharacter.fromDomain(it) }
                        ?: emptyList()
                )
            )
        } else {
            _resourceState.postValue(null)
        }

        mutableFetchingState.postValue(false)
    }
}
