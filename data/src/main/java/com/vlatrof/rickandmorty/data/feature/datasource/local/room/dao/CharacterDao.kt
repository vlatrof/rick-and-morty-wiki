package com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.vlatrof.rickandmorty.data.base.BaseDao
import com.vlatrof.rickandmorty.data.feature.model.character.CharacterEntity

@Dao
interface CharacterDao : BaseDao<CharacterEntity> {

    @Query("SELECT * FROM characters WHERE id = :id")
    override suspend fun getById(id: Int): CharacterEntity?

    @Query("SELECT * FROM characters WHERE id IN (:ids)")
    suspend fun getListByIds(ids: List<Int>): List<CharacterEntity>

    @Query(
        "SELECT COUNT() FROM characters " +
            "WHERE " +
            "name LIKE '%' || :name || '%' AND " +
            "species LIKE '%' || :species || '%' AND " +
            "type LIKE '%' || :type || '%' AND " +
            "gender LIKE :gender || '%' AND " +
            "status LIKE '%' || :status || '%' "
    )
    suspend fun getCount(
        name: String,
        species: String,
        type: String,
        gender: String,
        status: String
    ): Int

    @Query(
        "SELECT * FROM characters " +
            "WHERE " +
            "name LIKE '%' || :name || '%' AND " +
            "species LIKE '%' || :species || '%' AND " +
            "type LIKE '%' || :type || '%' AND " +
            "gender LIKE :gender || '%' AND " +
            "status LIKE '%' || :status || '%' " +
            "ORDER BY id ASC " +
            "LIMIT :limit " +
            "OFFSET :offset"
    )
    suspend fun getPage(
        limit: Int,
        offset: Int,
        name: String,
        species: String,
        type: String,
        gender: String,
        status: String
    ): List<CharacterEntity>
}
