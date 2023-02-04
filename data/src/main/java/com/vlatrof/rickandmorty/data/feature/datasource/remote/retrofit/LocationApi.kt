package com.vlatrof.rickandmorty.data.feature.datasource.remote.retrofit

import com.vlatrof.rickandmorty.data.feature.model.ApiPagedResponse
import com.vlatrof.rickandmorty.data.feature.model.location.LocationResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApi {

    @GET("location/")
    suspend fun getPage(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("dimension") dimension: String
    ): Response<ApiPagedResponse<LocationResponseDTO>>

    @GET("location/{id}")
    suspend fun getById(
        @Path("id") id: Int
    ): Response<LocationResponseDTO>

    @GET("location/{ids}")
    suspend fun getListByIds(
        @Path("ids") ids: String
    ): Response<List<LocationResponseDTO>>
}
