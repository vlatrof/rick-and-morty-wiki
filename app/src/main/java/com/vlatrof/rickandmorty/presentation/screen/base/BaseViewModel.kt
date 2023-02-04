package com.vlatrof.rickandmorty.presentation.screen.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vlatrof.rickandmorty.domain.model.common.DomainResult
import okio.IOException

abstract class BaseViewModel : ViewModel() {

    private val mutableNetworkConnectionErrorState = MutableLiveData<IOException?>()
    val networkConnectionErrorState: LiveData<IOException?> = mutableNetworkConnectionErrorState

    protected val mutableFetchingState = MutableLiveData<Boolean>()
    val fetchingState: LiveData<Boolean> = mutableFetchingState

    protected fun handleDomainResultRemoteError(result: DomainResult<*>) {
        if (result is DomainResult.Local) {
            mutableNetworkConnectionErrorState.postValue(result.remoteError)
        } else {
            mutableNetworkConnectionErrorState.postValue(null)
        }
    }
}
