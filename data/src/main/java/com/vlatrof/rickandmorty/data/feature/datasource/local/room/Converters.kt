package com.vlatrof.rickandmorty.data.feature.datasource.local.room

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun stringFromListOfInts(list: List<Int>): String =
        list.joinToString(",")

    @TypeConverter
    fun listOfIntsFromString(str: String): List<Int> {
        return if (str.isBlank()) {
            emptyList()
        } else {
            str.split(',').map { it.toInt() }
        }
    }
}
