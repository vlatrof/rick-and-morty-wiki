package com.vlatrof.rickandmorty.data.base

import androidx.room.Dao
import androidx.room.Upsert
import com.vlatrof.rickandmorty.data.base.model.BaseEntity

@Dao
interface BaseDao<T : BaseEntity> {

    suspend fun getById(id: Int): T?

    @Upsert
    suspend fun insert(entity: T)

    @Upsert
    suspend fun insertList(list: List<T>)
}
