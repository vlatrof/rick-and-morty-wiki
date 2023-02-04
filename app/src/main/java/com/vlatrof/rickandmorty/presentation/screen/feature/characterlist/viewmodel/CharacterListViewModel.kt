package com.vlatrof.rickandmorty.presentation.screen.feature.characterlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vlatrof.rickandmorty.domain.model.character.CharacterDomainFilter
import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter
import com.vlatrof.rickandmorty.domain.model.common.Page
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import com.vlatrof.rickandmorty.presentation.common.extension.copyAndMergeWith
import com.vlatrof.rickandmorty.presentation.model.character.PresentationCharacter
import com.vlatrof.rickandmorty.presentation.model.character.PresentationCharacterFilter
import com.vlatrof.rickandmorty.presentation.screen.base.BasePagedListViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val characterRepository: BaseRepository<DomainCharacter, CharacterDomainFilter>,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    textSearchDelayMillis: Long = DEFAULT_TEXT_SEARCH_DELAY
) : BasePagedListViewModel(ioDispatcher, mainDispatcher, textSearchDelayMillis) {

    private val _filterState = MutableLiveData(PresentationCharacterFilter.default)
    val filterState: LiveData<PresentationCharacterFilter> = _filterState

    private val _listState = MutableLiveData<List<PresentationCharacter>>(emptyList())
    val listState: LiveData<List<PresentationCharacter>> = _listState

    init {
        loadPage(1)
    }

    fun onClearFilterButtonClicked() {
        applyFilter(PresentationCharacterFilter(name = _filterState.value!!.name))
    }

    fun onApplyFilterButtonClicked(filter: PresentationCharacterFilter) {
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

            val result = characterRepository.getPage(pageNumber, filterState.value!!.toDomain())
            handleDomainResultRemoteError(result)

            when (val page = result.data) {
                is Page.Success -> {
                    _listState.postValue(
                        _listState.value!!.copyAndMergeWith(
                            page.value.map { PresentationCharacter.fromDomain(it) }
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

    private fun applyFilter(filter: PresentationCharacterFilter) {
        _filterState.value = filter
        _listState.value = emptyList()
        loadedPagesCount = 0
        loadPage(0)
    }
}
