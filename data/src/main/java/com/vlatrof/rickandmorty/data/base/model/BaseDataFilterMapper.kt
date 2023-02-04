package com.vlatrof.rickandmorty.data.base.model

import com.vlatrof.rickandmorty.domain.model.base.BaseDomainFilter

abstract class BaseDataFilterMapper<T : BaseDomainFilter, R : BaseDataFilter> {

    abstract fun domainToData(domainFilter: T): R
}
