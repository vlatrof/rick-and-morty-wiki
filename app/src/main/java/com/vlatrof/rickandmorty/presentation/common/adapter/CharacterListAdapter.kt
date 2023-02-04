package com.vlatrof.rickandmorty.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.vlatrof.rickandmorty.R
import com.vlatrof.rickandmorty.databinding.ListItemCharacterBinding
import com.vlatrof.rickandmorty.presentation.common.adapter.diffutil.CharacterListItemDiffUtil
import com.vlatrof.rickandmorty.presentation.common.extension.setDebouncedOnClickListener
import com.vlatrof.rickandmorty.presentation.model.character.PresentationCharacter

class CharacterListAdapter(
    private val onItemClickAction: (Int) -> Unit
) : ListAdapter<PresentationCharacter, CharacterListAdapter.CharacterListItemViewHolder>(
    CharacterListItemDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(item)
            binding.root.setDebouncedOnClickListener {
                onItemClickAction(item.id)
            }
        }
    }

    class CharacterListItemViewHolder(val binding: ListItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PresentationCharacter) {
            binding.apply {
                tvName.text = model.name
                with(root.context) {
                    tvSpecies.text = getString(R.string.character_item_species, model.species)
                    tvGender.text = getString(R.string.character_item_gender, model.gender)
                    tvStatus.text = getString(R.string.character_item_status, model.status)
                }
                ivImage.load(model.imageUrl) {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.character_image_placeholder)
                    error(R.drawable.character_image_placeholder)
                }
            }
        }
    }
}
