package com.vlatrof.rickandmorty.data.base

import com.vlatrof.rickandmorty.data.base.model.BaseDataFilter
import com.vlatrof.rickandmorty.data.base.model.BaseResponseDTO
import com.vlatrof.rickandmorty.data.feature.model.ApiPagedResponse
import com.vlatrof.rickandmorty.data.feature.model.DataPage
import com.vlatrof.rickandmorty.data.util.ResponseUtils
import retrofit2.Response
import java.io.IOException

abstract class BaseRemoteDataSource<T : BaseResponseDTO, F : BaseDataFilter> {

    suspend fun getById(id: Int): T? {
        val response = fetchById(id).also { checkServerError(it) }
        return if (response.isSuccessful) response.body()!! else null
    }

    suspend fun getListByIds(ids: List<Int>): List<T> {
        if (ids.isEmpty()) return emptyList()
        if (ids.size == 1) {
            val item = getById(ids.first())
            return if (item == null) emptyList() else listOf(item)
        }
        val response = fetchListByIds(ids).also { checkServerError(it) }
        return if (response.isSuccessful) response.body()!! else emptyList()
    }

    suspend fun getPage(pageNumber: Int, filter: F): DataPage<T> {
        val response = fetchPage(pageNumber, filter).also { checkServerError(it) }
        return if (response.isSuccessful) DataPage.Success(response.body()!!.results)
        else if (getPagesCount(filter) == 0) DataPage.NotFound
        else DataPage.OutOfPages
    }

    protected abstract suspend fun fetchById(id: Int): Response<T>

    protected abstract suspend fun fetchListByIds(ids: List<Int>): Response<List<T>>

    protected abstract suspend fun fetchPage(pageNumber: Int, filter: F):
        Response<ApiPagedResponse<T>>

    private suspend fun getPagesCount(filter: F): Int {
        val response = fetchPage(pageNumber = 0, filter = filter)
        checkServerError(response)
        return if (response.isSuccessful) response.body()!!.info.pages else 0
    }

    private fun <T> checkServerError(response: Response<T>) {
        if (response.code() >= ResponseUtils.INTERNAL_SERVER_ERROR_CODES_GROUP) {
            throw IOException(ResponseUtils.INTERNAL_SERVER_ERROR_MESSAGE)
        }
    }
}
