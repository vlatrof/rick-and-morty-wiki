package com.vlatrof.rickandmorty.data.base.model

import com.vlatrof.rickandmorty.domain.model.base.BaseDomainItem

interface BaseEntity {
    
    fun toDomain(): BaseDomainItem
}
