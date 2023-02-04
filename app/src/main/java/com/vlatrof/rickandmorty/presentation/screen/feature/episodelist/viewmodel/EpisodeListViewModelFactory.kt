package com.vlatrof.rickandmorty.presentation.screen.feature.episodelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vlatrof.rickandmorty.domain.model.episode.DomainEpisode
import com.vlatrof.rickandmorty.domain.model.episode.EpisodeDomainFilter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeListViewModelFactory @Inject constructor(
    private val episodeRepository: BaseRepository<DomainEpisode, EpisodeDomainFilter>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeListViewModel(
            episodeRepository = episodeRepository
        ) as T
    }
}
