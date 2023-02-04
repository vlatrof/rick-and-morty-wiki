package com.vlatrof.rickandmorty.data.feature.datasource.remote.retrofit

import com.vlatrof.rickandmorty.data.feature.model.ApiPagedResponse
import com.vlatrof.rickandmorty.data.feature.model.character.CharacterResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("character/")
    suspend fun getPage(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("species") species: String,
        @Query("type") type: String,
        @Query("gender") gender: String,
        @Query("status") status: String
    ): Response<ApiPagedResponse<CharacterResponseDTO>>

    @GET("character/{id}")
    suspend fun getById(
        @Path("id") id: Int
    ): Response<CharacterResponseDTO>

    @GET("character/{ids}")
    suspend fun getListByIds(
        @Path("ids") ids: String
    ): Response<List<CharacterResponseDTO>>
}
