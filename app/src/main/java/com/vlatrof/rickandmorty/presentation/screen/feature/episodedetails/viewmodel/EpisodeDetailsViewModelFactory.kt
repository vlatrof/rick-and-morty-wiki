package com.vlatrof.rickandmorty.presentation.screen.feature.episodedetails.viewmodel

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

class EpisodeDetailsViewModelFactory @AssistedInject constructor(
    @Assisted private val episodeId: Int,
    private val episodeRepository: BaseRepository<DomainEpisode, EpisodeDomainFilter>,
    private val characterRepository: BaseRepository<DomainCharacter, CharacterDomainFilter>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == EpisodeDetailsViewModel::class.java)
        return EpisodeDetailsViewModel(
            episodeId,
            episodeRepository,
            characterRepository
        ) as T
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted episodeId: Int): EpisodeDetailsViewModelFactory
    }
}
