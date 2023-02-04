package com.vlatrof.rickandmorty.presentation.screen.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BasePagedListViewModel(
    protected val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    private val textSearchDelayMillis: Long = DEFAULT_TEXT_SEARCH_DELAY
) : BaseViewModel() {

    protected var loadedPagesCount = 0

    private var loadByTextSearchJob: Job? = null

    fun onListScrolledToEnd() {
        loadPage(loadedPagesCount + 1)
    }

    fun onSearchTextChanged(value: String) {
        loadByTextSearchJob?.cancel()
        loadByTextSearchJob = viewModelScope.launch(mainDispatcher) {
            delay(textSearchDelayMillis)
            onNewSearchText(value)
        }
    }

    fun onRefreshLayoutSwiped() {
        clearList()
        loadedPagesCount = 0
        loadPage(1)
    }

    protected abstract fun clearList()

    protected abstract fun onNewSearchText(newText: String)

    protected abstract fun loadPage(pageNumber: Int)

    companion object {
        const val DEFAULT_TEXT_SEARCH_DELAY = 700L
    }
}
