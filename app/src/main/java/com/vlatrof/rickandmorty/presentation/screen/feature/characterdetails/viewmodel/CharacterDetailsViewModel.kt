package com.vlatrof.rickandmorty.presentation.screen.feature.characterdetails.viewmodel

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

class CharacterDetailsViewModel(
    private val characterId: Int,
    private val characterRepository: BaseRepository<DomainCharacter, CharacterDomainFilter>,
    private val episodeRepository: BaseRepository<DomainEpisode, EpisodeDomainFilter>,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {

    private val _resourceState =
        MutableLiveData<Pair<PresentationCharacter, List<PresentationEpisode>>?>()
    val resourceState: MutableLiveData<Pair<PresentationCharacter, List<PresentationEpisode>>?> =
        _resourceState

    init {
        loadResource(characterId)
    }

    fun onRefreshLayoutSwiped() {
        loadResource(characterId)
    }

    private fun loadResource(id: Int) = viewModelScope.launch(ioDispatcher) {
        mutableFetchingState.postValue(true)

        val characterResult = characterRepository.getById(id)
        handleDomainResultRemoteError(characterResult)

        val character = characterResult.data
        if (character != null) {
            val episodesResult = episodeRepository.getListByIds(character.episodeIds)
            _resourceState.postValue(
                Pair(
                    first = PresentationCharacter.fromDomain(character),
                    second = episodesResult.data?.map { PresentationEpisode.fromDomain(it) }
                        ?: emptyList()
                )
            )
        } else {
            _resourceState.postValue(null)
        }

        mutableFetchingState.postValue(false)
    }
}
