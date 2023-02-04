package com.vlatrof.rickandmorty.presentation.screen.feature.episodelist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vlatrof.rickandmorty.domain.model.common.DomainResult
import com.vlatrof.rickandmorty.domain.model.common.Page
import com.vlatrof.rickandmorty.domain.model.episode.DomainEpisode
import com.vlatrof.rickandmorty.domain.model.episode.EpisodeDomainFilter
import com.vlatrof.rickandmorty.domain.repository.BaseRepository
import com.vlatrof.rickandmorty.presentation.common.extension.copyAndMergeWith
import com.vlatrof.rickandmorty.presentation.model.episode.PresentationEpisode
import com.vlatrof.rickandmorty.presentation.model.episode.PresentationEpisodeFilter
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EpisodeListViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val episodeRepository = mockk<BaseRepository<DomainEpisode, EpisodeDomainFilter>>()
    private lateinit var viewModel: EpisodeListViewModel

    @Test
    fun `when initialized, should load first page and set it to listState`() = runTest {
        val dummyEpisode = DomainEpisode(
            id = 1,
            name = "Pilot",
            code = "S01E01",
            airDate = "December 2, 2013",
            characterIds = listOf(1, 2, 3)
        )
        val repositoryResponse =
            DomainResult.Remote(Page.Success(listOf(dummyEpisode, dummyEpisode)))
        val expectedResult =
            repositoryResponse.data!!.value.map { PresentationEpisode.fromDomain(it) }
        coEvery { episodeRepository.getPage(any(), any()) } returns repositoryResponse

        viewModel = EpisodeListViewModel(episodeRepository, UnconfinedTestDispatcher())

        coVerifySequence {
            episodeRepository.getPage(1, any())
        }
        val actualResult = viewModel.listState.value
        actualResult shouldBe expectedResult
    }

    @Test
    fun `when list scrolled to end should load next page and concat it with current listState`() =
        runTest {
            val dummyEpisode = DomainEpisode(
                id = 1,
                name = "Pilot",
                code = "S01E01",
                airDate = "December 2, 2013",
                characterIds = listOf(1, 2, 3)
            )
            val repositoryResponseFirst =
                DomainResult.Remote(Page.Success(listOf(dummyEpisode, dummyEpisode)))
            val repositoryResponseSecond =
                DomainResult.Remote(Page.Success(listOf(dummyEpisode)))
            val expectedResult = repositoryResponseFirst.data!!.value
                .copyAndMergeWith(repositoryResponseSecond.data!!.value)
                .map { PresentationEpisode.fromDomain(it) }
            coEvery { episodeRepository.getPage(1, any()) } returns repositoryResponseFirst
            coEvery { episodeRepository.getPage(2, any()) } returns repositoryResponseSecond

            viewModel = EpisodeListViewModel(episodeRepository, UnconfinedTestDispatcher())
            viewModel.onListScrolledToEnd()

            coVerifySequence {
                episodeRepository.getPage(1, any())
                episodeRepository.getPage(2, any())
            }
            val actualResult = viewModel.listState.value
            actualResult shouldBe expectedResult
        }

    @Test
    fun `when list swiped to refresh should clear list and load first page`() = runTest {
        val dummyEpisode = DomainEpisode(
            id = 1,
            name = "Pilot",
            code = "S01E01",
            airDate = "December 2, 2013",
            characterIds = listOf(1, 2, 3)
        )
        val repositoryResponse =
            DomainResult.Remote(Page.Success(listOf(dummyEpisode, dummyEpisode)))
        val expectedResult =
            repositoryResponse.data!!.value.map { PresentationEpisode.fromDomain(it) }
        coEvery { episodeRepository.getPage(1, any()) } returns repositoryResponse

        viewModel = EpisodeListViewModel(episodeRepository, UnconfinedTestDispatcher())
        viewModel.onRefreshLayoutSwiped()

        coVerifySequence {
            episodeRepository.getPage(1, any())
            episodeRepository.getPage(1, any())
        }
        val actualResult = viewModel.listState.value
        actualResult shouldBe expectedResult
    }

    @Test
    fun `when onApplyFilterButtonClicked called should change filterState`() = runTest {
        val dummyEpisode = DomainEpisode(
            id = 1,
            name = "Pilot",
            code = "S01E01",
            airDate = "December 2, 2013",
            characterIds = listOf(1, 2, 3)
        )
        val expectedResult = PresentationEpisodeFilter(
            code = "S01"
        )
        val repositoryResponse = DomainResult.Remote(Page.Success(listOf(dummyEpisode)))
        coEvery { episodeRepository.getPage(any(), any()) } returns repositoryResponse

        viewModel = EpisodeListViewModel(episodeRepository, UnconfinedTestDispatcher())
        viewModel.onApplyFilterButtonClicked(expectedResult)

        val actualResult = viewModel.filterState.value
        coVerifySequence {
            episodeRepository.getPage(1, PresentationEpisodeFilter.default.toDomain())
            episodeRepository.getPage(1, expectedResult.toDomain())
        }
        actualResult shouldBe expectedResult
    }

    @Test
    fun `when onApplyFilterButtonClicked called should reload list page matches filter`() =
        runTest {
            val dummyEpisode = DomainEpisode(
                id = 1,
                name = "Pilot",
                code = "S01E01",
                airDate = "December 2, 2013",
                characterIds = listOf(1, 2, 3)
            )
            val dummyFilter = PresentationEpisodeFilter(
                code = "S01"
            )
            val expectedResult = listOf(PresentationEpisode.fromDomain(dummyEpisode))
            val repositoryResponseFirst =
                DomainResult.Remote(Page.Success(emptyList<DomainEpisode>()))
            val repositoryResponseSecond = DomainResult.Remote(Page.Success(listOf(dummyEpisode)))
            coEvery {
                episodeRepository.getPage(
                    1,
                    PresentationEpisodeFilter.default.toDomain()
                )
            } returns repositoryResponseFirst
            coEvery {
                episodeRepository.getPage(
                    1,
                    dummyFilter.toDomain()
                )
            } returns repositoryResponseSecond

            viewModel = EpisodeListViewModel(episodeRepository, UnconfinedTestDispatcher())
            viewModel.onApplyFilterButtonClicked(dummyFilter)

            coVerifySequence {
                episodeRepository.getPage(1, PresentationEpisodeFilter.default.toDomain())
                episodeRepository.getPage(1, dummyFilter.toDomain())
            }
            val actualResult = viewModel.listState.value
            actualResult shouldBe expectedResult
        }

    @Test
    fun `when clearFilter called should clear filter state`() = runTest {
        val dummyFilter = PresentationEpisodeFilter(
            code = "S01"
        )
        val expectedResult = PresentationEpisodeFilter.default
        val repositoryResponse = DomainResult.Remote(Page.Success(emptyList<DomainEpisode>()))
        coEvery { episodeRepository.getPage(any(), any()) } returns repositoryResponse

        viewModel = EpisodeListViewModel(episodeRepository, UnconfinedTestDispatcher())
        viewModel.onApplyFilterButtonClicked(dummyFilter)
        viewModel.onClearFilterButtonClicked()

        coVerifySequence {
            episodeRepository.getPage(1, PresentationEpisodeFilter.default.toDomain())
            episodeRepository.getPage(1, dummyFilter.toDomain())
            episodeRepository.getPage(1, PresentationEpisodeFilter.default.toDomain())
        }
        val actualResult = viewModel.filterState.value
        actualResult shouldBe expectedResult
    }

    @Test
    fun `when onSearchTextChanged called should reload list page matches name value of filter`() =
        runTest {
            val dummyEpisode = DomainEpisode(
                id = 1,
                name = "Pilot",
                code = "S01E01",
                airDate = "December 2, 2013",
                characterIds = listOf(1, 2, 3)
            )
            val dummyFilter = PresentationEpisodeFilter(
                name = "Pilot"
            )
            val expectedResult = listOf(PresentationEpisode.fromDomain(dummyEpisode))
            val repositoryResponseFirst = DomainResult.Remote(Page.Success(listOf(dummyEpisode)))
            val repositoryResponseSecond = DomainResult.Remote(Page.Success(listOf(dummyEpisode)))
            coEvery {
                episodeRepository.getPage(1, PresentationEpisodeFilter.default.toDomain())
            } returns repositoryResponseFirst
            coEvery {
                episodeRepository.getPage(1, dummyFilter.toDomain())
            } returns repositoryResponseSecond

            viewModel = EpisodeListViewModel(
                episodeRepository,
                UnconfinedTestDispatcher(),
                UnconfinedTestDispatcher(),
                textSearchDelayMillis = 0
            )
            viewModel.onSearchTextChanged("Pilot")

            coVerifySequence {
                episodeRepository.getPage(1, PresentationEpisodeFilter.default.toDomain())
                episodeRepository.getPage(1, dummyFilter.toDomain())
            }
            val actualResult = viewModel.listState.value
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
                characterIds = listOf(1, 2, 3)
            )
            val dummyIOException = IOException("dummy IOException")
            val expectedResult = dummyIOException
            val repositoryResponse =
                DomainResult.Local(Page.Success(listOf(dummyEpisode)), dummyIOException)
            coEvery {
                episodeRepository.getPage(1, any())
            } returns repositoryResponse

            viewModel = EpisodeListViewModel(
                episodeRepository,
                UnconfinedTestDispatcher(),
                UnconfinedTestDispatcher()
            )

            coVerifySequence { episodeRepository.getPage(1, any()) }
            val actualResult = viewModel.networkConnectionErrorState.value
            actualResult shouldBe expectedResult
        }

    @Test
    fun `when received Result-Remote should set network connection state with null`() = runTest {
        val dummyEpisode = DomainEpisode(
            id = 1,
            name = "Pilot",
            code = "S01E01",
            airDate = "December 2, 2013",
            characterIds = listOf(1, 2, 3)
        )
        val expectedResult = null
        val repositoryResponse = DomainResult.Remote(Page.Success(listOf(dummyEpisode)))
        coEvery { episodeRepository.getPage(1, any()) } returns repositoryResponse

        viewModel = EpisodeListViewModel(
            episodeRepository,
            UnconfinedTestDispatcher(),
            UnconfinedTestDispatcher()
        )

        coVerifySequence { episodeRepository.getPage(1, any()) }
        val actualResult = viewModel.networkConnectionErrorState.value
        actualResult shouldBe expectedResult
    }
}
