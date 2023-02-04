package com.vlatrof.rickandmorty.data.feature.datasource.remote.retrofit

import com.vlatrof.rickandmorty.data.feature.model.ApiPagedResponse
import com.vlatrof.rickandmorty.data.feature.model.episode.EpisodeResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApi {

    @GET("episode/")
    suspend fun getPage(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("episode") episode: String
    ): Response<ApiPagedResponse<EpisodeResponseDTO>>

    @GET("episode/{id}")
    suspend fun getById(
        @Path("id") id: Int
    ): Response<EpisodeResponseDTO>

    @GET("episode/{ids}")
    suspend fun getListByIds(
        @Path("ids") ids: String
    ): Response<List<EpisodeResponseDTO>>
}
