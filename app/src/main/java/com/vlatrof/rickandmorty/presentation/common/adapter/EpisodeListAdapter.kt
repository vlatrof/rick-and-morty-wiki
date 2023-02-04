package com.vlatrof.rickandmorty.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vlatrof.rickandmorty.R
import com.vlatrof.rickandmorty.databinding.ListItemEpisodeBinding
import com.vlatrof.rickandmorty.presentation.common.adapter.diffutil.EpisodeListItemDiffUtil
import com.vlatrof.rickandmorty.presentation.common.extension.setDebouncedOnClickListener
import com.vlatrof.rickandmorty.presentation.model.episode.PresentationEpisode

class EpisodeListAdapter(
    private val onItemClickAction: (Int) -> Unit
) : ListAdapter<PresentationEpisode, EpisodeListAdapter.EpisodeListItemViewHolder>(
    EpisodeListItemDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemEpisodeBinding.inflate(inflater, parent, false)
        return EpisodeListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(item)
            binding.root.setDebouncedOnClickListener {
                onItemClickAction(item.id)
            }
        }
    }

    class EpisodeListItemViewHolder(val binding: ListItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PresentationEpisode) {
            binding.apply {
                with(root.context) {
                    tvName.text = model.name
                    tvCode.text = getString(R.string.episode_item_code, model.code)
                    tvAirDate.text = getString(R.string.episode_item_air_date, model.airDate)
                }
            }
        }
    }
}
