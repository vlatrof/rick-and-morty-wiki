package com.vlatrof.rickandmorty.presentation.screen.feature.characterlist

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.ArrayRes
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import com.vlatrof.rickandmorty.R
import com.vlatrof.rickandmorty.databinding.FragmentCharacterFilterBinding
import com.vlatrof.rickandmorty.presentation.common.extension.setDebouncedOnClickListener
import com.vlatrof.rickandmorty.presentation.model.character.PresentationCharacterFilter
import com.vlatrof.rickandmorty.presentation.screen.base.BaseDialogFragment
import com.vlatrof.rickandmorty.presentation.screen.feature.characterlist.viewmodel.CharacterListViewModel

class CharacterListFilterFragment : BaseDialogFragment<FragmentCharacterFilterBinding>(
    R.layout.fragment_character_filter
) {

    private val viewModel: CharacterListViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClearButton()
        setupCancelButton()
        setupApplyButton()
    }

    override fun onResume() {
        super.onResume()
        setupMenuGender()
        setupMenuStatus()
        observeFilterState()
    }

    override fun createBinding(view: View): FragmentCharacterFilterBinding {
        return FragmentCharacterFilterBinding.bind(view)
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
                    PresentationCharacterFilter(
                        species = speciesInput.text.toString().trim(),
                        type = typeInput.text.toString().trim(),
                        gender = genderInput.text.toString().trim(),
                        status = statusInput.text.toString().trim()
                    )
                }
            )
            dismiss()
        }
    }

    private fun setupMenuStatus() {
        binding.apply {
            setupMenuWithResource(tilStatus, statusInput, R.array.character_status_filter_items)
        }
    }

    private fun setupMenuGender() {
        binding.apply {
            setupMenuWithResource(tilGender, genderInput, R.array.character_gender_filter_items)
        }
    }

    private fun observeFilterState() {
        viewModel.filterState.observe(viewLifecycleOwner) { filter ->
            binding.apply {
                speciesInput.setText(filter.species)
                typeInput.setText(filter.type)
                genderInput.setText(filter.gender)
                statusInput.setText(filter.status)
            }
        }
    }

    private fun setupMenuWithResource(
        textInputLayout: TextInputLayout,
        autoCompleteTextView: AutoCompleteTextView,
        @ArrayRes arrayResId: Int
    ) {
        autoCompleteTextView.setAdapter(
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(arrayResId)
            )
        )
        autoCompleteTextView.doAfterTextChanged { value ->
            textInputLayout.endIconMode =
                if (value.toString().isEmpty()) TextInputLayout.END_ICON_DROPDOWN_MENU
                else TextInputLayout.END_ICON_CLEAR_TEXT
        }
    }
}
