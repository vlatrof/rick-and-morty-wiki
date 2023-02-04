package com.vlatrof.rickandmorty.presentation.common.extension

fun <T> List<T>.copyAndMergeWith(other: List<T>): List<T> =
    this.toMutableList().apply { addAll(other) }
