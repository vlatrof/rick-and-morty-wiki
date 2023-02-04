package com.vlatrof.rickandmorty.presentation.screen.feature.episodelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vlatrof.rickandmorty.domain.model.common.Page
import com.vlatrof.rickandmorty.domain.model.episode.DomainEpisode
import com.vlatrof.rickandmorty.domain.model.episode.EpisodeDomainFilter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import com.vlatrof.rickandmorty.presentation.common.extension.copyAndMergeWith
import com.vlatrof.rickandmorty.presentation.model.episode.PresentationEpisode
import com.vlatrof.rickandmorty.presentation.model.episode.PresentationEpisodeFilter
import com.vlatrof.rickandmorty.presentation.screen.base.BasePagedListViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodeListViewModel(
    private val episodeRepository: BaseRepository<DomainEpisode, EpisodeDomainFilter>,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    textSearchDelayMillis: Long = DEFAULT_TEXT_SEARCH_DELAY
) : BasePagedListViewModel(ioDispatcher, mainDispatcher, textSearchDelayMillis) {

    private val _filterState = MutableLiveData(PresentationEpisodeFilter.default)
    val filterState: LiveData<PresentationEpisodeFilter> = _filterState

    private val _listState = MutableLiveData<List<PresentationEpisode>>(emptyList())
    val listState: LiveData<List<PresentationEpisode>> = _listState

    init {
        loadPage(1)
    }

    fun onClearFilterButtonClicked() {
        applyFilter(PresentationEpisodeFilter(name = _filterState.value!!.name))
    }

    fun onApplyFilterButtonClicked(filter: PresentationEpisodeFilter) {
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

            val result = episodeRepository.getPage(pageNumber, filterState.value!!.toDomain())
            handleDomainResultRemoteError(result)

            when (val page = result.data) {
                is Page.Success -> {
                    _listState.postValue(
                        _listState.value!!.copyAndMergeWith(
                            page.value.map { PresentationEpisode.fromDomain(it) }
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

    private fun applyFilter(filter: PresentationEpisodeFilter) {
        _filterState.value = filter
        _listState.value = emptyList()
        loadedPagesCount = 0
        loadPage(1)
    }
}
