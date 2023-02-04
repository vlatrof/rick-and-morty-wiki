package com.vlatrof.rickandmorty.presentation.screen.feature.characterlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlatrof.rickandmorty.domain.model.character.CharacterDomainFilter
import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterListViewModelFactory @Inject constructor(
    private val characterRepository: BaseRepository<DomainCharacter, CharacterDomainFilter>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterListViewModel(
            characterRepository = characterRepository
        ) as T
    }
}
