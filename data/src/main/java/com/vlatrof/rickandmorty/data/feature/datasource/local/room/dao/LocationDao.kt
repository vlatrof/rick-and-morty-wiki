package com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.vlatrof.rickandmorty.data.base.BaseDao
import com.vlatrof.rickandmorty.data.feature.model.location.LocationEntity

@Dao
interface LocationDao : BaseDao<LocationEntity> {

    @Query("SELECT * FROM locations WHERE id = :id")
    override suspend fun getById(id: Int): LocationEntity?

    @Query("SELECT * FROM locations WHERE id IN (:ids)")
    suspend fun getListByIds(ids: List<Int>): List<LocationEntity>

    @Query(
        "SELECT COUNT() FROM locations " +
            "WHERE " +
            "name LIKE '%' || :name || '%' AND " +
            "type LIKE '%' || :type || '%' AND " +
            "dimension LIKE '%' || :dimension || '%' "
    )
    suspend fun getCount(name: String, type: String, dimension: String): Int

    @Query(
        "SELECT * FROM locations " +
            "WHERE " +
            "name LIKE '%' || :name || '%' AND " +
            "type LIKE '%' || :type || '%' AND " +
            "dimension LIKE '%' || :dimension || '%' " +
            "ORDER BY id ASC " +
            "LIMIT :limit " +
            "OFFSET :offset"
    )
    suspend fun getPage(
        limit: Int,
        offset: Int,
        name: String,
        type: String,
        dimension: String
    ): List<LocationEntity>
}
