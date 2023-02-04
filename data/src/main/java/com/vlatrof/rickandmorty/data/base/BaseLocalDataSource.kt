package com.vlatrof.rickandmorty.data.base

import com.vlatrof.rickandmorty.data.BuildConfig
import com.vlatrof.rickandmorty.data.base.model.BaseDataFilter
import com.vlatrof.rickandmorty.data.base.model.BaseEntity
import com.vlatrof.rickandmorty.data.feature.model.DataPage
import kotlin.math.ceil

abstract class BaseLocalDataSource<T : BaseEntity, F : BaseDataFilter>(
    private val dao: BaseDao<T>
) {

    abstract suspend fun getListByIds(ids: List<Int>): List<T>

    suspend fun insert(entity: T) {
        dao.insert(entity)
    }

    suspend fun insertList(list: List<T>) {
        dao.insertList(list)
    }

    suspend fun getById(id: Int): T? = dao.getById(id)

    suspend fun getPage(pageNumber: Int, filter: F): DataPage<T> {
        val pageSize = BuildConfig.PAGE_SIZE
        val totalCount = getCount(filter)
        val totalPages = ceil(totalCount.toDouble() / pageSize).toInt()

        return if (totalPages == 0) {
            DataPage.NotFound
        } else if (pageNumber > totalPages) {
            DataPage.OutOfPages
        } else {
            val page = getPage(pageSize, pageNumber, filter)
            DataPage.Success(page)
        }
    }

    protected abstract suspend fun getCount(filter: F): Int

    protected abstract suspend fun getPage(pageSize: Int, pageNumber: Int, filter: F): List<T>
}
