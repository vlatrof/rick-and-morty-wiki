package com.vlatrof.rickandmorty.app.di

import com.vlatrof.rickandmorty.data.di.DatabaseModule
import com.vlatrof.rickandmorty.data.di.NetworkModule
import com.vlatrof.rickandmorty.data.di.RepositoryModule
import com.vlatrof.rickandmorty.presentation.screen.feature.characterdetails.CharacterDetailsFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.characterlist.CharacterListFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.episodedetails.EpisodeDetailsFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.episodelist.EpisodeListFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.locationdetails.LocationDetailsFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.locationlist.LocationListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        ApplicationModule::class
    ]
)
interface AppComponent {

    fun inject(characterListFragment: CharacterListFragment)
    fun inject(characterDetailsFragment: CharacterDetailsFragment)
    fun inject(locationListFragment: LocationListFragment)
    fun inject(locationDetailsFragment: LocationDetailsFragment)
    fun inject(episodeListFragment: EpisodeListFragment)
    fun inject(episodeDetailsFragment: EpisodeDetailsFragment)
}
