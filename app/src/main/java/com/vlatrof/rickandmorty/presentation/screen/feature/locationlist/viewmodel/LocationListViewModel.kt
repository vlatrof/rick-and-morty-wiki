package com.vlatrof.rickandmorty.presentation.screen.feature.locationlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vlatrof.rickandmorty.domain.model.common.Page
import com.vlatrof.rickandmorty.domain.model.location.DomainLocation
import com.vlatrof.rickandmorty.domain.model.location.LocationDomainFilter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import com.vlatrof.rickandmorty.presentation.common.extension.copyAndMergeWith
import com.vlatrof.rickandmorty.presentation.model.location.PresentationLocation
import com.vlatrof.rickandmorty.presentation.model.location.PresentationLocationFilter
import com.vlatrof.rickandmorty.presentation.screen.base.BasePagedListViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationListViewModel(
    private val locationRepository: BaseRepository<DomainLocation, LocationDomainFilter>,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    textSearchDelayMillis: Long = DEFAULT_TEXT_SEARCH_DELAY
) : BasePagedListViewModel(ioDispatcher, mainDispatcher, textSearchDelayMillis) {

    private val _filterState = MutableLiveData(PresentationLocationFilter.default)
    val filterState: LiveData<PresentationLocationFilter> = _filterState

    private val _listState = MutableLiveData<List<PresentationLocation>>(emptyList())
    val listState: LiveData<List<PresentationLocation>> = _listState

    init {
        loadPage(1)
    }

    fun onClearFilterButtonClicked() {
        applyFilter(PresentationLocationFilter(name = _filterState.value!!.name))
    }

    fun onApplyFilterButtonClicked(filter: PresentationLocationFilter) {
        applyFilter(filter.copy(name = _filterState.value!!.name))
    }

    override fun clearList() {
        _listState.value = emptyList()
    }

    override fun onNewSearchText(newText: String) {
        applyFilter(_filterState.value!!.copy(name = newText.trim()))
    }

    override fun loadPage(pageNumber: Int) {
        viewModelScope.launch(ioDispatcher) {
            mutableFetchingState.postValue(true)

            val result = locationRepository.getPage(pageNumber, filterState.value!!.toDomain())
            handleDomainResultRemoteError(result)

            when (val page = result.data) {
                is Page.Success -> {
                    _listState.postValue(
                        _listState.value!!.copyAndMergeWith(
                            page.value.map { PresentationLocation.fromDomain(it) }
                        )
                    )
                    loadedPagesCount++
                }
                Page.EndOfPages -> {}
                null -> _listState.postValue(emptyList())
            }

            mutableFetchingState.postValue(false)
        }
    }

    private fun applyFilter(filter: PresentationLocationFilter) {
        _filterState.value = filter
        _listState.value = emptyList()
        loadedPagesCount = 0
        loadPage(0)
    }
}
