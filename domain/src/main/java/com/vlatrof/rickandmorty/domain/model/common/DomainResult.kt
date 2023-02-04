package com.vlatrof.rickandmorty.domain.model.common

import java.io.IOException

sealed class DomainResult<out T>(val data: T?) {
    class Remote<out T>(data: T?) : DomainResult<T>(data)
    class Local<out T>(data: T?, val remoteError: IOException) : DomainResult<T>(data)
}

sealed class Page<out T> {
    class Success<out T>(val value: List<T>) : Page<T>()
    object EndOfPages : Page<Nothing>()
}
