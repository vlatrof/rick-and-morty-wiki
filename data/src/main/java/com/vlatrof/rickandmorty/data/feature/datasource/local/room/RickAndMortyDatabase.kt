package com.vlatrof.rickandmorty.data.feature.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao.CharacterDao
import com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao.EpisodeDao
import com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao.LocationDao
import com.vlatrof.rickandmorty.data.feature.model.character.CharacterEntity
import com.vlatrof.rickandmorty.data.feature.model.episode.EpisodeEntity
import com.vlatrof.rickandmorty.data.feature.model.location.LocationEntity

@Database(
    entities = [
        CharacterEntity::class,
        LocationEntity::class,
        EpisodeEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract val characterDao: CharacterDao
    abstract val locationDao: LocationDao
    abstract val episodeDao: EpisodeDao
}
