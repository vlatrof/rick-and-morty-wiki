package com.vlatrof.rickandmorty.presentation.screen.feature.episodedetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vlatrof.rickandmorty.domain.model.character.CharacterDomainFilter
import com.vlatrof.rickandmorty.domain.model.character.DomainCharacter
import com.vlatrof.rickandmorty.domain.model.common.DomainResult
import com.vlatrof.rickandmorty.domain.model.episode.DomainEpisode
import com.vlatrof.rickandmorty.domain.model.episode.EpisodeDomainFilter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import com.vlatrof.rickandmorty.presentation.model.character.PresentationCharacter
import com.vlatrof.rickandmorty.presentation.model.episode.PresentationEpisode
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class EpisodeDetailsViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val episodeRepository = mockk<BaseRepository<DomainEpisode, EpisodeDomainFilter>>()
    private val characterRepository =
        mockk<BaseRepository<DomainCharacter, CharacterDomainFilter>>()
    private lateinit var viewModel: EpisodeDetailsViewModel

    @Test
    fun `when initialized, should load resource and set it to resourceState`() = runTest {
        val dummyEpisode = DomainEpisode(
            id = 1,
            name = "Pilot",
            code = "S01E01",
            airDate = "December 2, 2013",
            characterIds = listOf(1, 2)
        )
        val dummyCharacter = DomainCharacter(1, "", "", "", "", "", "", 1, "", 1, "", listOf(1, 2))
        val episodeRepositoryResponse = DomainResult.Remote(dummyEpisode)
        val characterRepositoryResponse =
            DomainResult.Remote(listOf(dummyCharacter, dummyCharacter))
        val expectedResult = Pair(
            PresentationEpisode.fromDomain(episodeRepositoryResponse.data!!),
            characterRepositoryResponse.data!!.map { PresentationCharacter.fromDomain(it) }
        )
        coEvery { episodeRepository.getById(dummyEpisode.id) } returns episodeRepositoryResponse
        coEvery { characterRepository.getListByIds(dummyEpisode.characterIds) } returns characterRepositoryResponse

        viewModel = EpisodeDetailsViewModel(
            dummyEpisode.id,
            episodeRepository,
            characterRepository,
            UnconfinedTestDispatcher()
        )

        coVerifySequence {
            episodeRepository.getById(dummyEpisode.id)
            characterRepository.getListByIds(dummyEpisode.characterIds)
        }
        val actualResult = viewModel.resourceState.value
        actualResult shouldBe expectedResult
    }

    @Test
    fun `when resource not found, should set resourceState with null`() = runTest {
        val dummyEpisode = DomainEpisode(
            id = 1,
            name = "Pilot",
            code = "S01E01",
            airDate = "December 2, 2013",
            characterIds = listOf(1, 2)
        )
        val episodeRepositoryResponse = DomainResult.Remote(null)
        val expectedResult = (null)
        coEvery { episodeRepository.getById(dummyEpisode.id) } returns episodeRepositoryResponse

        viewModel = EpisodeDetailsViewModel(
            dummyEpisode.id,
            episodeRepository,
            characterRepository,
            UnconfinedTestDispatcher()
        )

        coVerifySequence {
            episodeRepository.getById(dummyEpisode.id)
        }
        val actualResult = viewModel.resourceState.value
        actualResult shouldBe expectedResult
    }

    @Test
    fun `when onRefreshLayoutSwipedCalled should reload resource`() = runTest {
        val dummyEpisode = DomainEpisode(
            id = 1,
            name = "Pilot",
            code = "S01E01",
            airDate = "December 2, 2013",
            characterIds = listOf(1, 2)
        )
        val dummyCharacter = DomainCharacter(1, "", "", "", "", "", "", 1, "", 1, "", listOf(1, 2))
        val episodeRepositoryResponse = DomainResult.Remote(dummyEpisode)
        val characterRepositoryResponse =
            DomainResult.Remote(listOf(dummyCharacter, dummyCharacter))
        val expectedResult = Pair(
            PresentationEpisode.fromDomain(episodeRepositoryResponse.data!!),
            characterRepositoryResponse.data!!.map { PresentationCharacter.fromDomain(it) }
        )
        coEvery { episodeRepository.getById(dummyEpisode.id) } returns episodeRepositoryResponse
        coEvery { characterRepository.getListByIds(dummyEpisode.characterIds) } returns characterRepositoryResponse

        viewModel = EpisodeDetailsViewModel(
            dummyEpisode.id,
            episodeRepository,
            characterRepository,
            UnconfinedTestDispatcher()
        )
        viewModel.onRefreshLayoutSwiped()

        coVerifySequence {
            episodeRepository.getById(dummyEpisode.id)
            characterRepository.getListByIds(dummyEpisode.characterIds)
            episodeRepository.getById(dummyEpisode.id)
            characterRepository.getListByIds(dummyEpisode.characterIds)
        }
        val actualResult = viewModel.resourceState.value
        actualResult shouldBe expectedResult
    }

    @Test
    fun `when received Result-Local should set network connection state with result not null IOException`() =
        runTest {
            val dummyEpisode = DomainEpisode(
                id = 1,
                name = "Pilot",
                code = "S01E01",
                airDate = "December 2, 2013",
                characterIds = listOf(1, 2)
            )
            val dummyIOException = IOException("dummy IOException")
            val dummyCharacter =
                DomainCharacter(1, "", "", "", "", "", "", 1, "", 1, "", listOf(1, 2))
            val episodeRepositoryResponse = DomainResult.Local(dummyEpisode, dummyIOException)
            val characterRepositoryResponse =
                DomainResult.Local(listOf(dummyCharacter, dummyCharacter), dummyIOException)
            val expectedResult = dummyIOException
            coEvery { episodeRepository.getById(dummyEpisode.id) } returns episodeRepositoryResponse
            coEvery { characterRepository.getListByIds(dummyEpisode.characterIds) } returns characterRepositoryResponse

            viewModel = EpisodeDetailsViewModel(
                dummyEpisode.id,
                episodeRepository,
                characterRepository,
                UnconfinedTestDispatcher()
            )

            coVerifySequence {
                episodeRepository.getById(dummyEpisode.id)
                characterRepository.getListByIds(dummyEpisode.characterIds)
            }
            val actualResult = viewModel.networkConnectionErrorState.value
            actualResult shouldBe expectedResult
        }
}
