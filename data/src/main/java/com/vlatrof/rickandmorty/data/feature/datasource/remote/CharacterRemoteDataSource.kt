package com.vlatrof.rickandmorty.data.feature.datasource.remote

import com.vlatrof.rickandmorty.data.base.BaseRemoteDataSource
import com.vlatrof.rickandmorty.data.feature.datasource.remote.retrofit.CharacterApi
import com.vlatrof.rickandmorty.data.feature.model.character.CharacterDataFilter
import com.vlatrof.rickandmorty.data.feature.model.character.CharacterResponseDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRemoteDataSource @Inject constructor(
    private val characterApi: CharacterApi
) : BaseRemoteDataSource<CharacterResponseDTO, CharacterDataFilter>() {

    override suspend fun fetchById(id: Int) = characterApi.getById(id)

    override suspend fun fetchListByIds(ids: List<Int>) =
        characterApi.getListByIds(ids.joinToString(","))

    override suspend fun fetchPage(pageNumber: Int, filter: CharacterDataFilter) =
        characterApi.getPage(
            page = pageNumber,
            name = filter.name,
            species = filter.species,
            type = filter.type,
            gender = filter.gender,
            status = filter.status
        )
}
