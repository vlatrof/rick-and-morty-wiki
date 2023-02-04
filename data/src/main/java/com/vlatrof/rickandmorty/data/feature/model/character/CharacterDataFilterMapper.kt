package com.vlatrof.rickandmorty.data.feature.model.character

import com.vlatrof.rickandmorty.data.base.model.BaseDataFilterMapper
import com.vlatrof.rickandmorty.domain.model.character.CharacterDomainFilter
import javax.inject.Inject

class CharacterDataFilterMapper @Inject constructor() :
    BaseDataFilterMapper<CharacterDomainFilter, CharacterDataFilter>() {

    override fun domainToData(domainFilter: CharacterDomainFilter) = CharacterDataFilter(
        name = domainFilter.name,
        species = domainFilter.species,
        type = domainFilter.type,
        gender = domainFilter.gender,
        status = domainFilter.status
    )
}
