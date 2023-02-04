package com.vlatrof.rickandmorty.data.di

import com.vlatrof.rickandmorty.data.feature.repository.CharacterRepositoryImpl
import com.vlatrof.rickandmorty.data.feature.repository.EpisodeRepositoryImpl
import com.vlatrof.rickandmorty.data.feature.repository.LocationRepositoryImpl
import com.vlatrof.rickandmorty.domain.model.character.CharacterDomainFilter
import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter
import com.vlatrof.rickandmorty.domain.model.episode.DomainEpisode
import com.vlatrof.rickandmorty.domain.model.episode.EpisodeDomainFilter
import com.vlatrof.rickandmorty.domain.model.location.DomainLocation
import com.vlatrof.rickandmorty.domain.model.location.LocationDomainFilter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): BaseRepository<DomainCharacter, CharacterDomainFilter>

    @Singleton
    @Binds
    fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): BaseRepository<DomainLocation, LocationDomainFilter>

    @Singleton
    @Binds
    fun bindEpisodeRepository(
        episodeRepositoryImpl: EpisodeRepositoryImpl
    ): BaseRepository<DomainEpisode, EpisodeDomainFilter>
}
