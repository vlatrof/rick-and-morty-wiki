package com.vlatrof.rickandmorty.presentation.common.navigation

interface NavigationManager {

    fun goBack()

    fun launchCharacterDetailsFragment(characterId: Int)

    fun launchLocationDetailsFragment(locationId: Int)

    fun launchEpisodeDetailsFragment(episodeId: Int)
}
