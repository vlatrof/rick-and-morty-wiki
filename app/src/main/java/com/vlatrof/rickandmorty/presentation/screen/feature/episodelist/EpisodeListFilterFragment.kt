package com.vlatrof.rickandmorty.presentation.screen.feature.episodelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.vlatrof.rickandmorty.R
import com.vlatrof.rickandmorty.databinding.FragmentEpisodeFilterBinding
import com.vlatrof.rickandmorty.presentation.common.extension.setDebouncedOnClickListener
import com.vlatrof.rickandmorty.presentation.model.episode.PresentationEpisodeFilter
import com.vlatrof.rickandmorty.presentation.screen.base.BaseDialogFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.episodelist.viewmodel.EpisodeListViewModel

class EpisodeListFilterFragment : BaseDialogFragment<FragmentEpisodeFilterBinding>(
    R.layout.fragment_episode_filter
) {

    private val viewModel: EpisodeListViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClearButton()
        setupCancelButton()
        setupApplyButton()
        observeFilterState()
    }

    override fun createBinding(view: View): FragmentEpisodeFilterBinding {
        return FragmentEpisodeFilterBinding.bind(view)
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
                    PresentationEpisodeFilter(
                        code = codeInput.text.toString().trim()
                    )
                }
            )
            dismiss()
        }
    }

    private fun observeFilterState() {
        viewModel.filterState.observe(viewLifecycleOwner) { filter ->
            binding.apply {
                codeInput.setText(filter.code)
            }
        }
    }
}
