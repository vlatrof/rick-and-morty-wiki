package com.vlatrof.rickandmorty.data.feature.datasource.local

import com.vlatrof.rickandmorty.data.base.BaseLocalDataSource
import com.vlatrof.rickandmorty.data.feature.datasource.local.room.dao.CharacterDao
import com.vlatrof.rickandmorty.data.feature.model.character.CharacterDataFilter
import com.vlatrof.rickandmorty.data.feature.model.character.CharacterEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterLocalDataSource @Inject constructor(
    private val characterDao: CharacterDao
) : BaseLocalDataSource<CharacterEntity, CharacterDataFilter>(
    characterDao
) {

    override suspend fun getListByIds(ids: List<Int>) = characterDao.getListByIds(ids)

    override suspend fun getCount(filter: CharacterDataFilter) =
        characterDao.getCount(
            name = filter.name,
            species = filter.species,
            type = filter.type,
            gender = filter.gender,
            status = filter.status
        )

    override suspend fun getPage(pageSize: Int, pageNumber: Int, filter: CharacterDataFilter) =
        characterDao.getPage(
            limit = pageSize,
            offset = pageSize * (pageNumber - 1),
            species = filter.species,
            name = filter.name,
            type = filter.type,
            gender = filter.gender,
            status = filter.status
        )
}
