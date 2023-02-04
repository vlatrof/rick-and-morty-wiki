package com.vlatrof.rickandmorty.presentation.screen.feature.characterdetails

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.vlatrof.rickandmorty.R
import com.vlatrof.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.vlatrof.rickandmorty.presentation.common.adapter.EpisodeListAdapter
import com.vlatrof.rickandmorty.presentation.common.extension.addGapItemDecoration
import com.vlatrof.rickandmorty.presentation.common.extension.getDimen
import com.vlatrof.rickandmorty.presentation.common.extension.removeElevation
import com.vlatrof.rickandmorty.presentation.common.extension.removeOnClickListener
import com.vlatrof.rickandmorty.presentation.common.extension.setDebouncedOnClickListener
import com.vlatrof.rickandmorty.presentation.common.extension.showToast
import com.vlatrof.rickandmorty.presentation.model.character.PresentationCharacter
import com.vlatrof.rickandmorty.presentation.model.episode.PresentationEpisode
import com.vlatrof.rickandmorty.presentation.screen.base.BaseFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.characterdetails.viewmodel.CharacterDetailsViewModel
import com.vlatrof.rickandmorty.presentation.screen.feature.characterdetails.viewmodel.CharacterDetailsViewModelFactory
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class CharacterDetailsFragment : BaseFragment<FragmentCharacterDetailsBinding>(
    R.layout.fragment_character_details
) {

    @Inject
    lateinit var viewModelFactory: CharacterDetailsViewModelFactory.Factory
    private val viewModel: CharacterDetailsViewModel by viewModels {
        viewModelFactory.create(characterId = requireCharacterIdArg())
    }
    private lateinit var adapter: EpisodeListAdapter

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

    override fun createBinding(view: View): FragmentCharacterDetailsBinding {
        return FragmentCharacterDetailsBinding.bind(view)
    }

    private fun setupAdapter() {
        adapter = EpisodeListAdapter(
            onItemClickAction = { episodeId ->
                navigationManager.launchEpisodeDetailsFragment(episodeId)
            }
        )
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            navigationManager.goBack()
        }
    }

    private fun setupRecyclerView() {
        binding.rvEpisodeList.apply {
            adapter = this@CharacterDetailsFragment.adapter
            addGapItemDecoration(
                gapPx = getDimen(R.dimen.list_item_gap),
                spanCount = 1
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

    private fun populateUi(resource: Pair<PresentationCharacter, List<PresentationEpisode>>) {
        val character = resource.first
        val episodes = resource.second

        binding.apply {
            toolbar.title = character.name
            loadCharacterImage(ivImage, character.imageUrl)
            tvName.text = character.name
            tvSpecies.text = getString(R.string.character_item_species, character.species)
            tvType.text = getString(R.string.character_item_type, character.type)
            tvGender.text = getString(R.string.character_item_gender, character.gender)
            tvStatus.text = getString(R.string.character_item_status, character.status)
            tvOriginLocation.text = getString(
                R.string.character_item_origin_location,
                character.originLocationName
            )
            tvLastLocation.text = getString(
                R.string.character_item_last_location,
                character.lastLocationName
            )

            if (character.originLocationId != null) {
                cvOriginLocation.setDebouncedOnClickListener {
                    navigationManager.launchLocationDetailsFragment(character.originLocationId)
                }
            } else {
                cvOriginLocation.removeOnClickListener()
                cvOriginLocation.removeElevation()
            }

            if (character.lastLocationId != null) {
                cvLastLocation.setDebouncedOnClickListener {
                    navigationManager.launchLocationDetailsFragment(character.lastLocationId)
                }
            } else {
                cvLastLocation.removeOnClickListener()
                cvLastLocation.removeElevation()
            }

            cvOriginLocation.visibility = View.VISIBLE
            cvLastLocation.visibility = View.VISIBLE
            tvEpisodes.visibility = View.VISIBLE
            adapter.submitList(episodes)
        }
    }

    private fun loadCharacterImage(view: ImageView, url: String) {
        view.load(url) {
            transformations(CircleCropTransformation())
            placeholder(R.drawable.character_image_placeholder)
            error(R.drawable.character_image_placeholder)
        }
    }

    private fun requireCharacterIdArg(): Int {
        return requireNotNull(arguments?.getInt(ARG_KEY_CHARACTER_ID)) {
            showToast(R.string.error_message_something_went_wrong)
            navigationManager.goBack()
        }
    }

    companion object {

        const val ARG_KEY_CHARACTER_ID = "CHARACTER_ID"

        fun newInstance(characterId: Int) = CharacterDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_KEY_CHARACTER_ID, characterId)
            }
        }
    }
}
