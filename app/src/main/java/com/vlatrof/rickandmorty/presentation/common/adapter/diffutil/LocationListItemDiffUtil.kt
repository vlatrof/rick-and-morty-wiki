package com.vlatrof.rickandmorty.presentation.common.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.vlatrof.rickandmorty.presentation.model.location.PresentationLocation

object LocationListItemDiffUtil : DiffUtil.ItemCallback<PresentationLocation>() {

    override fun areItemsTheSame(oldItem: PresentationLocation, newItem: PresentationLocation) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PresentationLocation, newItem: PresentationLocation) =
        oldItem == newItem
}
