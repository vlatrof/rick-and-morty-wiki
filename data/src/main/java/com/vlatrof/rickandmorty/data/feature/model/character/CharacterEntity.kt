package com.vlatrof.rickandmorty.data.feature.model.character

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vlatrof.rickandmorty.data.base.model.BaseEntity
import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "species")
    val species: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "origin_location_name")
    val originLocationName: String,
    @ColumnInfo(name = "origin_location_id")
    val originLocationId: Int?,
    @ColumnInfo(name = "last_location_name")
    val lastLocationName: String,
    @ColumnInfo(name = "last_location_id")
    val lastLocationId: Int?,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "episode_ids")
    val episodeIds: List<Int>

) : BaseEntity {

    override fun toDomain() = DomainCharacter(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        type = type,
        originLocationName = originLocationName,
        originLocationId = originLocationId,
        lastLocationName = lastLocationName,
        lastLocationId = lastLocationId,
        imageUrl = imageUrl,
        episodeIds = episodeIds
    )
}
