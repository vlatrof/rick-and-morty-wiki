package com.vlatrof.rickandmorty.domain.repository

import com.vlatrof.rickandmorty.domain.model.base.BaseDomainFilter
import com.vlatrof.rickandmorty.domain.model.base.BaseDomainItem
import com.vlatrof.rickandmorty.domain.model.common.DomainResult
import com.vlatrof.rickandmorty.domain.model.common.Page

interface BaseRepository<T : BaseDomainItem, F : BaseDomainFilter> {

    suspend fun getById(id: Int): DomainResult<T?>

    suspend fun getListByIds(ids: List<Int>): DomainResult<List<T>>

    suspend fun getPage(pageNumber: Int, filter: F): DomainResult<Page<T>>
}
