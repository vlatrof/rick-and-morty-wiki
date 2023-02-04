package com.vlatrof.rickandmorty.presentation.common.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.vlatrof.rickandmorty.presentation.model.episode.PresentationEpisode

object EpisodeListItemDiffUtil : DiffUtil.ItemCallback<PresentationEpisode>() {

    override fun areItemsTheSame(oldItem: PresentationEpisode, newItem: PresentationEpisode) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PresentationEpisode, newItem: PresentationEpisode) =
        oldItem == newItem
}
