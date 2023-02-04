package com.vlatrof.rickandmorty.presentation.screen.feature.episodedetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.vlatrof.rickandmorty.R
import com.vlatrof.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.vlatrof.rickandmorty.presentation.common.adapter.CharacterListAdapter
import com.vlatrof.rickandmorty.presentation.common.extension.addGapItemDecoration
import com.vlatrof.rickandmorty.presentation.common.extension.getDimen
import com.vlatrof.rickandmorty.presentation.common.extension.showToast
import com.vlatrof.rickandmorty.presentation.model.character.PresentationCharacter
import com.vlatrof.rickandmorty.presentation.model.episode.PresentationEpisode
import com.vlatrof.rickandmorty.presentation.screen.base.BaseFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.episodedetails.viewmodel.EpisodeDetailsViewModel
import com.vlatrof.rickandmorty.presentation.screen.feature.episodedetails.viewmodel.EpisodeDetailsViewModelFactory
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class EpisodeDetailsFragment : BaseFragment<FragmentEpisodeDetailsBinding>(
    R.layout.fragment_episode_details
) {

    @Inject
    lateinit var viewModelFactory: EpisodeDetailsViewModelFactory.Factory
    private val viewModel: EpisodeDetailsViewModel by viewModels {
        viewModelFactory.create(episodeId = requireEpisodeIdArg())
    }
    private lateinit var adapter: CharacterListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupSwipeToRefresh()
        observeConnectionState()
        observeResourceState()
        observeFetchingState()
    }

    override fun createBinding(view: View): FragmentEpisodeDetailsBinding {
        return FragmentEpisodeDetailsBinding.bind(view)
    }

    private fun setupAdapter() {
        adapter = CharacterListAdapter(
            onItemClickAction = { characterId ->
                navigationManager.launchCharacterDetailsFragment(characterId)
            }
        )
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            navigationManager.goBack()
        }
    }

    private fun setupRecyclerView() {
        binding.rvCharacterList.apply {
            adapter = this@EpisodeDetailsFragment.adapter
            addGapItemDecoration(
                gapPx = getDimen(R.dimen.list_item_gap),
                spanCount = 2
            )
        }
    }

    private fun setupSwipeToRefresh() {
        binding.srlRefresh.setOnRefreshListener {
            viewModel.onRefreshLayoutSwiped()
        }
    }

    private fun observeConnectionState() {
        viewModel.networkConnectionErrorState.observe(viewLifecycleOwner) { error ->
            binding.tvErrorBar.apply {
                when (error) {
                    is UnknownHostException -> {
                        visibility = View.VISIBLE
                        text = getString(R.string.error_message_network_connection_error)
                    }
                    is IOException -> {
                        visibility = View.VISIBLE
                        text = getString(R.string.error_message_unknown_network_error)
                    }
                    null -> {
                        visibility = View.GONE
                        text = null
                    }
                    else -> {
                        visibility = View.VISIBLE
                        text = getString(R.string.error_message_something_went_wrong)
                    }
                }
            }
        }
    }

    private fun observeFetchingState() {
        viewModel.fetchingState.observe(viewLifecycleOwner) { isFetching ->
            binding.apply {
                if (isFetching) {
                    pbLoading.visibility = View.VISIBLE
                } else {
                    pbLoading.visibility = View.GONE
                    srlRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun observeResourceState() {
        viewModel.resourceState.observe(viewLifecycleOwner) { resource ->
            if (resource == null) {
                showToast(R.string.error_message_something_went_wrong)
                navigationManager.goBack()
            } else {
                populateUi(resource)
            }
        }
    }

    private fun populateUi(resource: Pair<PresentationEpisode, List<PresentationCharacter>>) {
        val episode = resource.first
        val residents = resource.second

        binding.apply {
            toolbar.title = episode.name
            tvName.text = episode.name
            tvCode.text = getString(R.string.episode_item_code, episode.code)
            tvAirDate.text = getString(R.string.episode_item_air_date, episode.airDate)
            tvCharacters.visibility = View.VISIBLE
            adapter.submitList(residents)
        }
    }

    private fun requireEpisodeIdArg(): Int {
        return requireNotNull(arguments?.getInt(ARG_KEY_EPISODE_ID)) {
            showToast(R.string.error_message_something_went_wrong)
            navigationManager.goBack()
        }
    }

    companion object {

        const val ARG_KEY_EPISODE_ID = "EPISODE_ID"

        fun newInstance(episodeId: Int) = EpisodeDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_KEY_EPISODE_ID, episodeId)
            }
        }
    }
}
