package com.vlatrof.rickandmorty.presentation.screen.feature.locationlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.vlatrof.rickandmorty.R
import com.vlatrof.rickandmorty.databinding.FragmentLocationFilterBinding
import com.vlatrof.rickandmorty.presentation.common.extension.setDebouncedOnClickListener
import com.vlatrof.rickandmorty.presentation.model.location.PresentationLocationFilter
import com.vlatrof.rickandmorty.presentation.screen.base.BaseDialogFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.locationlist.viewmodel.LocationListViewModel

class LocationListFilterFragment : BaseDialogFragment<FragmentLocationFilterBinding>(
    R.layout.fragment_location_filter
) {

    private val viewModel: LocationListViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClearButton()
        setupCancelButton()
        setupApplyButton()
        observeFilterState()
    }

    override fun createBinding(view: View): FragmentLocationFilterBinding {
        return FragmentLocationFilterBinding.bind(view)
    }

    private fun setupClearButton() {
        binding.btnClear.setDebouncedOnClickListener {
            viewModel.onClearFilterButtonClicked()
            dismiss()
        }
    }

    private fun setupCancelButton() {
        binding.btnCancel.setDebouncedOnClickListener {
            dismiss()
        }
    }

    private fun setupApplyButton() {
        binding.btnApply.setDebouncedOnClickListener {
            viewModel.onApplyFilterButtonClicked(
                with(binding) {
                    PresentationLocationFilter(
                        type = typeInput.text.toString().trim(),
                        dimension = dimensionInput.text.toString().trim()
                    )
                }
            )
            dismiss()
        }
    }

    private fun observeFilterState() {
        viewModel.filterState.observe(viewLifecycleOwner) { filter ->
            binding.apply {
                typeInput.setText(filter.type)
                dimensionInput.setText(filter.dimension)
            }
        }
    }
}
