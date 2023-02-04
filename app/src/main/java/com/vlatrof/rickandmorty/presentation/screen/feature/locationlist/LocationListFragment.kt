package com.vlatrof.rickandmorty.presentation.screen.feature.locationlist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlatrof.rickandmorty.R
import com.vlatrof.rickandmorty.databinding.FragmentLocationListBinding
import com.vlatrof.rickandmorty.presentation.common.adapter.LocationListAdapter
import com.vlatrof.rickandmorty.presentation.common.extension.addGapItemDecoration
import com.vlatrof.rickandmorty.presentation.common.extension.addOnScrollToEndListener
import com.vlatrof.rickandmorty.presentation.common.extension.doOnQueryTextChanged
import com.vlatrof.rickandmorty.presentation.common.extension.getDimen
import com.vlatrof.rickandmorty.presentation.common.extension.setColorFilterPrimaryColor
import com.vlatrof.rickandmorty.presentation.common.extension.setDebouncedOnClickListener
import com.vlatrof.rickandmorty.presentation.common.util.ClassUtils
import com.vlatrof.rickandmorty.presentation.screen.base.BackStackRootFragment
import com.vlatrof.rickandmorty.presentation.screen.base.BaseFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.locationlist.viewmodel.LocationListViewModel
import com.vlatrof.rickandmorty.presentation.screen.feature.locationlist.viewmodel.LocationListViewModelFactory
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class LocationListFragment :
    BaseFragment<FragmentLocationListBinding>(R.layout.fragment_location_list),
    BackStackRootFragment {

    @Inject
    lateinit var locationListViewModelFactory: LocationListViewModelFactory
    private val viewModel: LocationListViewModel by viewModels { locationListViewModelFactory }
    private lateinit var adapter: LocationListAdapter
    private var ignoreSearchTextChanged = false

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
        ignoreSearchTextChanged = true
        setupSearchField()
        setupFilterButton()
        setupRecyclerView()
        setupSwipeToRefresh()
        observeConnectionState()
        observeListState()
        observeFilterState()
        observeFetchingState()
    }

    override fun createBinding(view: View): FragmentLocationListBinding {
        return FragmentLocationListBinding.bind(view)
    }

    override fun onTabReselected() {
        binding.rvList.smoothScrollToPosition(0)
    }

    private fun setupAdapter() {
        adapter = LocationListAdapter(
            onItemClickAction = { locationId ->
                navigationManager.launchLocationDetailsFragment(locationId)
            }
        )
    }

    private fun setupSearchField() {
        binding.svSearch.doOnQueryTextChanged { text ->
            if (!ignoreSearchTextChanged) {
                viewModel.onSearchTextChanged(text)
            }
            ignoreSearchTextChanged = false
        }
    }

    private fun setupFilterButton() {
        binding.btnFilter.setDebouncedOnClickListener {
            LocationListFilterFragment()
                .show(
                    childFragmentManager,
                    ClassUtils.generateTag(LocationListFilterFragment::class.java)
                )
        }
    }

    private fun setupRecyclerView() {
        binding.rvList.apply {
            adapter = this@LocationListFragment.adapter
            addGapItemDecoration(
                gapPx = getDimen(R.dimen.list_item_gap),
                spanCount = 2
            )
            addOnScrollToEndListener(
                layoutManager = layoutManager as LinearLayoutManager,
                onScrolledToEndAction = { viewModel.onListScrolledToEnd() }
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

    private fun observeListState() {
        viewModel.listState.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.tvNotFound.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun observeFilterState() {
        viewModel.filterState.observe(viewLifecycleOwner) { filter ->
            if (filter.type.isNotBlank() ||
                filter.dimension.isNotBlank()
            ) {
                binding.btnFilter.setColorFilterPrimaryColor()
            } else {
                binding.btnFilter.clearColorFilter()
            }
        }
    }

    private fun observeFetchingState() {
        viewModel.fetchingState.observe(viewLifecycleOwner) { isFetching ->
            binding.apply {
                if (isFetching) {
                    btnFilter.visibility = View.GONE
                    pbLoading.visibility = View.VISIBLE
                    tvNotFound.visibility = View.GONE
                } else {
                    pbLoading.visibility = View.GONE
                    btnFilter.visibility = View.VISIBLE
                    srlRefresh.isRefreshing = false
                }
            }
        }
    }
}
