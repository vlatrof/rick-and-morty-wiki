package com.vlatrof.rickandmorty.presentation.common.util

object ClassUtils {
    fun generateTag(clazz: Class<*>) = clazz.name + "@" + clazz.hashCode()
}
