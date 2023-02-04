package com.vlatrof.rickandmorty.data.di

import android.content.Context
import androidx.room.Room
import com.vlatrof.rickandmorty.data.BuildConfig
import com.vlatrof.rickandmorty.data.feature.datasource.local.room.RickAndMortyDatabase
import com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao.CharacterDao
import com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao.EpisodeDao
import com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao.LocationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(
        context: Context
    ): RickAndMortyDatabase =
        Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideCharacterDao(
        rickAndMortyDatabase: RickAndMortyDatabase
    ): CharacterDao =
        rickAndMortyDatabase.characterDao

    @Provides
    @Singleton
    fun provideLocationDao(
        rickAndMortyDatabase: RickAndMortyDatabase
    ): LocationDao =
        rickAndMortyDatabase.locationDao

    @Provides
    @Singleton
    fun provideEpisodeDao(
        rickAndMortyDatabase: RickAndMortyDatabase
    ): EpisodeDao =
        rickAndMortyDatabase.episodeDao
}
