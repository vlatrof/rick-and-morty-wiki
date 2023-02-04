package com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.vlatrof.rickandmorty.data.base.BaseDao
import com.vlatrof.rickandmorty.data.feature.model.episode.EpisodeEntity

@Dao
interface EpisodeDao : BaseDao<EpisodeEntity> {

    @Query("SELECT * FROM episodes WHERE id = :id")
    override suspend fun getById(id: Int): EpisodeEntity?

    @Query("SELECT * FROM episodes WHERE id IN (:ids)")
    suspend fun getListByIds(ids: List<Int>): List<EpisodeEntity>
    
    @Query(
        "SELECT COUNT() FROM episodes " +
            "WHERE " +
            "name LIKE '%' || :name || '%' AND " +
            "code LIKE '%' || :code || '%' "
    )
    suspend fun getCount(name: String, code: String): Int

    @Query(
        "SELECT * FROM episodes " +
            "WHERE " +
            "name LIKE '%' || :name || '%' AND " +
            "code LIKE '%' || :code || '%' " +
            "ORDER BY id ASC " +
            "LIMIT :limit " +
            "OFFSET :offset"
    )
    suspend fun getPage(
        limit: Int,
        offset: Int,
        name: String,
        code: String
    ): List<EpisodeEntity>
}
