package com.vlatrof.rickandmorty.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vlatrof.rickandmorty.R
import com.vlatrof.rickandmorty.databinding.ListItemLocationBinding
import com.vlatrof.rickandmorty.presentation.common.adapter.diffutil.LocationListItemDiffUtil
import com.vlatrof.rickandmorty.presentation.common.extension.EMPTY
import com.vlatrof.rickandmorty.presentation.common.extension.setDebouncedOnClickListener
import com.vlatrof.rickandmorty.presentation.model.location.PresentationLocation

class LocationListAdapter(
    private val onItemClickAction: (Int) -> Unit
) : ListAdapter<PresentationLocation, LocationListAdapter.LocationListItemViewHolder>(
    LocationListItemDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemLocationBinding.inflate(inflater, parent, false)
        return LocationListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            bind(item)
            binding.root.setDebouncedOnClickListener {
                onItemClickAction(item.id)
            }
        }
    }

    class LocationListItemViewHolder(val binding: ListItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PresentationLocation) {
            binding.apply {
                with(root.context) {
                    val type = getString(R.string.location_item_type, model.type)

                    val dimension = getString(
                        R.string.location_item_dimension,
                        model.dimension.replace(
                            getString(R.string.str_component_dimension),
                            String.EMPTY
                        ).trim()
                    )

                    tvName.text = model.name
                    tvType.text = type
                    tvDimension.text = dimension
                }
            }
        }
    }
}
