package com.vlatrof.rickandmorty.presentation.common.navigation

import androidx.fragment.app.Fragment
import com.vlatrof.rickandmorty.presentation.common.util.ClassUtils
import com.vlatrof.rickandmorty.presentation.screen.feature.characterlist.CharacterListFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.episodelist.EpisodeListFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.locationlist.LocationListFragment

sealed class BackStack {

    abstract fun createRootFragmentInstance(): Fragment

    val tag get() = ClassUtils.generateTag(this::class.java)

    object Characters : BackStack() {
        override fun createRootFragmentInstance() = CharacterListFragment()
    }

    object Locations : BackStack() {
        override fun createRootFragmentInstance() = LocationListFragment()
    }

    object Episodes : BackStack() {
        override fun createRootFragmentInstance() = EpisodeListFragment()
    }

    companion object {

        val default = Characters

        fun getByTag(tag: String): BackStack? = when (tag) {
            Characters.tag -> Characters
            Locations.tag -> Locations
            Episodes.tag -> Episodes
            else -> null
        }
    }
}
