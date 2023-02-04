package com.vlatrof.rickandmorty.presentation.screen.feature.characterdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlatrof.rickandmorty.domain.model.character.CharacterDomainFilter
import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter
import com.vlatrof.rickandmorty.domain.model.episode.DomainEpisode
import com.vlatrof.rickandmorty.domain.model.episode.EpisodeDomainFilter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CharacterDetailsViewModelFactory @AssistedInject constructor(
    @Assisted private val characterId: Int,
    private val characterRepository: BaseRepository<DomainCharacter, CharacterDomainFilter>,
    private val episodeRepository: BaseRepository<DomainEpisode, EpisodeDomainFilter>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == CharacterDetailsViewModel::class.java)
        return CharacterDetailsViewModel(
            characterId,
            characterRepository,
            episodeRepository
        ) as T
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted characterId: Int): CharacterDetailsViewModelFactory
    }
}
