package com.vlatrof.rickandmorty.data.base

import com.vlatrof.rickandmorty.data.base.model.BaseDataFilter
import com.vlatrof.rickandmorty.data.base.model.BaseDataFilterMapper
import com.vlatrof.rickandmorty.data.base.model.BaseEntity
import com.vlatrof.rickandmorty.data.base.model.BaseResponseDTO
import com.vlatrof.rickandmorty.data.feature.model.DataPage
import com.vlatrof.rickandmorty.domain.model.base.BaseDomainFilter
import com.vlatrof.rickandmorty.domain.model.base.BaseDomainItem
import com.vlatrof.rickandmorty.domain.model.common.DomainResult
import com.vlatrof.rickandmorty.domain.model.common.Page
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import java.io.IOException

@Suppress("UNCHECKED_CAST")
abstract class BaseRepositoryImpl<
    DomainItem : BaseDomainItem,
    DomainFilter : BaseDomainFilter,
    ResponseDTO : BaseResponseDTO,
    Entity : BaseEntity,
    DataFilter : BaseDataFilter>(

    private val baseRemoteDataSource: BaseRemoteDataSource<ResponseDTO, DataFilter>,
    private val baseLocalDataSource: BaseLocalDataSource<Entity, DataFilter>,
    private val dataFilterMapper: BaseDataFilterMapper<DomainFilter, DataFilter>

) : BaseRepository<DomainItem, DomainFilter> {

    override suspend fun getById(id: Int): DomainResult<DomainItem?> {
        return try {
            val response = baseRemoteDataSource.getById(id)
            response?.let {
                baseLocalDataSource.insert(it.toEntity() as Entity)
            }
            DomainResult.Remote(data = response?.toDomain() as DomainItem)
        } catch (exception: IOException) {
            DomainResult.Local(
                data = baseLocalDataSource.getById(id)?.toDomain() as DomainItem,
                remoteError = exception
            )
        }
    }

    override suspend fun getListByIds(ids: List<Int>): DomainResult<List<DomainItem>> {
        return try {
            val list = baseRemoteDataSource.getListByIds(ids)
            if (list.isNotEmpty()) {
                baseLocalDataSource.insertList(list.map { it.toEntity() as Entity })
            }
            DomainResult.Remote(data = list.map { it.toDomain() as DomainItem })
        } catch (exception: IOException) {
            DomainResult.Local(
                data = baseLocalDataSource.getListByIds(ids).map { it.toDomain() as DomainItem },
                remoteError = exception
            )
        }
    }

    override suspend fun getPage(
        pageNumber: Int,
        filter: DomainFilter
    ): DomainResult<Page<DomainItem>> {
        val dataFilter = dataFilterMapper.domainToData(filter)
        return try {
            val page = baseRemoteDataSource.getPage(pageNumber, dataFilter)
            if (page is DataPage.Success) {
                baseLocalDataSource.insertList(page.data.map { it.toEntity() as Entity })
            }
            remotePageToDomain(page)
        } catch (exception: IOException) {
            DomainResult.Local(
                data = pageFromCache(pageNumber, dataFilter),
                remoteError = exception
            )
        }
    }

    private suspend fun pageFromCache(pageNumber: Int, filter: DataFilter): Page<DomainItem>? =
        when (val page = baseLocalDataSource.getPage(pageNumber, filter)) {
            is DataPage.Success -> Page.Success(page.data.map { it.toDomain() as DomainItem })
            is DataPage.OutOfPages -> Page.EndOfPages
            is DataPage.NotFound -> null
        }

    private fun remotePageToDomain(
        page: DataPage<ResponseDTO>
    ): DomainResult.Remote<Page<DomainItem>> =
        when (page) {
            is DataPage.Success -> DomainResult.Remote(
                data = Page.Success(page.data.map { it.toDomain() as DomainItem })
            )
            DataPage.OutOfPages -> DomainResult.Remote(Page.EndOfPages)
            DataPage.NotFound -> DomainResult.Remote(null)
        }
}
