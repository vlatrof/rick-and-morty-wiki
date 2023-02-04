package com.vlatrof.rickandmorty.data.util

object ResponseUtils {

    fun urlToId(url: String) = url.substringAfterLast("/").toIntOrNull()
    
    const val IF_BLANK_PLACEHOLDER = "None"
    const val INTERNAL_SERVER_ERROR_CODES_GROUP = 500
    const val INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error"
}
