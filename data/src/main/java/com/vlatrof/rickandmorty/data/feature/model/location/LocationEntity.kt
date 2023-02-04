package com.vlatrof.rickandmorty.data.feature.model.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vlatrof.rickandmorty.data.base.model.BaseEntity
import com.vlatrof.rickandmorty.domain.model.location.DomainLocation

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "dimension")
    val dimension: String,
    @ColumnInfo(name = "resident_ids")
    val residentIds: List<Int>

) : BaseEntity {

    override fun toDomain() = DomainLocation(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residentIds = residentIds
    )
}
