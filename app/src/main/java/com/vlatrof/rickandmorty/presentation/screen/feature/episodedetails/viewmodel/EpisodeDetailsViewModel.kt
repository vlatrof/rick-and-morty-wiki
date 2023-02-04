package com.vlatrof.rickandmorty.presentation.screen.feature.episodedetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vlatrof.rickandmorty.domain.model.character.CharacterDomainFilter
import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter
import com.vlatrof.rickandmorty.domain.model.episode.DomainEpisode
import com.vlatrof.rickandmorty.domain.model.episode.EpisodeDomainFilter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import com.vlatrof.rickandmorty.presentation.model.character.PresentationCharacter
import com.vlatrof.rickandmorty.presentation.model.episode.PresentationEpisode
import com.vlatrof.rickandmorty.presentation.screen.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodeDetailsViewModel(
    private val episodeId: Int,
    private val episodeRepository: BaseRepository<DomainEpisode, EpisodeDomainFilter>,
    private val characterRepository: BaseRepository<DomainCharacter, CharacterDomainFilter>,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val _resourceState =
        MutableLiveData<Pair<PresentationEpisode, List<PresentationCharacter>>?>()
    val resourceState: MutableLiveData<Pair<PresentationEpisode, List<PresentationCharacter>>?> =
        _resourceState

    init {
        loadResource(episodeId)
    }

    fun onRefreshLayoutSwiped() {
        loadResource(episodeId)
    }

    private fun loadResource(id: Int) = viewModelScope.launch(ioDispatcher) {
        mutableFetchingState.postValue(true)

        val episodeResult = episodeRepository.getById(id)
        handleDomainResultRemoteError(episodeResult)

        val episode = episodeResult.data
        if (episode != null) {
            val charactersResult = characterRepository.getListByIds(episode.characterIds)
            _resourceState.postValue(
                Pair(
                    first = PresentationEpisode.fromDomain(episode),
                    second = charactersResult.data?.map { PresentationCharacter.fromDomain(it) }
                        ?: emptyList()
                )
            )
        } else {
            _resourceState.postValue(null)
        }

        mutableFetchingState.postValue(false)
    }
}
