package com.vlatrof.rickandmorty.data.feature.model

sealed class DataPage<out T> {
    class Success<out T>(val data: List<T>) : DataPage<T>()
    object OutOfPages : DataPage<Nothing>()
    object NotFound : DataPage<Nothing>()
}
