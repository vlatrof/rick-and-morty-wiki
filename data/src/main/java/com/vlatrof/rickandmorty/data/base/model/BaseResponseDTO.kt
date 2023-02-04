package com.vlatrof.rickandmorty.data.base.model

import com.vlatrof.rickandmorty.domain.model.base.BaseDomainItem

interface BaseResponseDTO {

    fun toDomain(): BaseDomainItem

    fun toEntity(): BaseEntity
}
