package com.vlatrof.rickandmorty.data.feature.model.episode

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vlatrof.rickandmorty.data.base.model.BaseEntity
import com.vlatrof.rickandmorty.domain.model.episode.DomainEpisode

@Entity(tableName = "episodes")
data class EpisodeEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "air_date")
    val airDate: String,
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "character_ids")
    val characterIds: List<Int>

) : BaseEntity {

    override fun toDomain() = DomainEpisode(
        id = id,
        name = name,
        code = code,
        airDate = airDate,
        characterIds = characterIds
    )
}
