package com.vlatrof.rickandmorty.presentation.common.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.vlatrof.rickandmorty.presentation.model.character.PresentationCharacter

object CharacterListItemDiffUtil : DiffUtil.ItemCallback<PresentationCharacter>() {

    override fun areItemsTheSame(oldItem: PresentationCharacter, newItem: PresentationCharacter) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PresentationCharacter, newItem: PresentationCharacter) =
        oldItem == newItem
}
