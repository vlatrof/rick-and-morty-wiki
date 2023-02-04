package com.vlatrof.rickandmorty.data.feature.repository

import com.vlatrof.rickandmorty.data.base.BaseRepositoryImpl
import com.vlatrof.rickandmorty.data.feature.datasource.local.CharacterLocalDataSource
import com.vlatrof.rickandmorty.data.feature.datasource.remote.CharacterRemoteDataSource
import com.vlatrof.rickandmorty.data.feature.model.character.CharacterDataFilter
import com.vlatrof.rickandmorty.data.feature.model.character.CharacterDataFilterMapper
import com.vlatrof.rickandmorty.data.feature.model.character.CharacterEntity
import com.vlatrof.rickandmorty.data.feature.model.character.CharacterResponseDTO
import com.vlatrof.rickandmorty.domain.model.character.CharacterDomainFilter
import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    remoteDataSource: CharacterRemoteDataSource,
    localDataSource: CharacterLocalDataSource,
    dataFilterMapper: CharacterDataFilterMapper

) : BaseRepositoryImpl<DomainCharacter, CharacterDomainFilter, CharacterResponseDTO,
    CharacterEntity, CharacterDataFilter>(

    remoteDataSource,
    localDataSource,
    dataFilterMapper
)
