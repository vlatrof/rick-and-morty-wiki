package com.vlatrof.rickandmorty.presentation.screen.feature.locationdetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.vlatrof.rickandmorty.R
import com.vlatrof.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.vlatrof.rickandmorty.presentation.common.adapter.CharacterListAdapter
import com.vlatrof.rickandmorty.presentation.common.extension.EMPTY
import com.vlatrof.rickandmorty.presentation.common.extension.addGapItemDecoration
import com.vlatrof.rickandmorty.presentation.common.extension.getDimen
import com.vlatrof.rickandmorty.presentation.common.extension.showToast
import com.vlatrof.rickandmorty.presentation.model.character.PresentationCharacter
import com.vlatrof.rickandmorty.presentation.model.location.PresentationLocation
import com.vlatrof.rickandmorty.presentation.screen.base.BaseFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.locationdetails.viewmodel.LocationDetailsViewModel
import com.vlatrof.rickandmorty.presentation.screen.feature.locationdetails.viewmodel.LocationDetailsViewModelFactory
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class LocationDetailsFragment : BaseFragment<FragmentLocationDetailsBinding>(
    R.layout.fragment_location_details
) {

    @Inject
    lateinit var viewModelFactory: LocationDetailsViewModelFactory.Factory
    private val viewModel: LocationDetailsViewModel by viewModels {
        viewModelFactory.create(locationId = requireLocationIdArg())
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

    override fun createBinding(view: View): FragmentLocationDetailsBinding {
        return FragmentLocationDetailsBinding.bind(view)
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
            adapter = this@LocationDetailsFragment.adapter
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

    private fun populateUi(resource: Pair<PresentationLocation, List<PresentationCharacter>>) {
        val location = resource.first
        val residents = resource.second

        binding.apply {
            val type = getString(R.string.location_item_type, location.type)

            val dimension = getString(
                R.string.location_item_dimension,
                location.dimension.replace(
                    getString(R.string.str_component_dimension),
                    String.EMPTY
                ).trim()
            )

            toolbar.title = location.name
            tvName.text = location.name
            tvType.text = type
            tvDimension.text = dimension

            if (residents.isEmpty()) {
                tvResidents.text = getString(R.string.location_details_screen_subtitle_no_residents)
            } else {
                tvResidents.text = getString(R.string.location_details_screen_subtitle_residents)
                adapter.submitList(residents)
            }
        }
    }

    private fun requireLocationIdArg(): Int {
        return requireNotNull(arguments?.getInt(ARG_KEY_LOCATION_ID)) {
            showToast(R.string.error_message_something_went_wrong)
            navigationManager.goBack()
        }
    }

    companion object {

        const val ARG_KEY_LOCATION_ID = "LOCATION_ID"

        fun newInstance(locationId: Int) = LocationDetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_KEY_LOCATION_ID, locationId)
            }
        }
    }
}
