package com.vlatrof.rickandmorty.presentation.common.extension

import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.vlatrof.rickandmorty.presentation.common.decoration.GapItemDecoration
import com.vlatrof.rickandmorty.presentation.common.listener.DebouncedOnClickListener
import com.vlatrof.rickandmorty.presentation.common.listener.OnScrollToEndListener

fun RecyclerView.addOnScrollToEndListener(
    debounceDelayMillis: Long = OnScrollToEndListener.DEFAULT_DELAY_MILLIS,
    layoutManager: LinearLayoutManager,
    onScrolledToEndAction: () -> Unit
) {
    addOnScrollListener(
        OnScrollToEndListener(
            debounceDelayMillis = debounceDelayMillis,
            layoutManager = layoutManager,
            onScrolledToEndAction = onScrolledToEndAction
        )
    )
}

fun RecyclerView.addGapItemDecoration(gapPx: Int, spanCount: Int = 1) {
    addItemDecoration(GapItemDecoration(gapPx = gapPx, spanCount = spanCount))
}

fun View.setDebouncedOnClickListener(
    delayMills: Long = DebouncedOnClickListener.DEFAULT_DELAY_MILLS,
    onClickAction: () -> Unit
) {
    isClickable = true
    setOnClickListener(DebouncedOnClickListener(delayMills, onClickAction))
}

fun View.removeOnClickListener() {
    setOnClickListener(null)
    isClickable = false
}

fun View.removeElevation() {
    elevation = 0F
}

fun SearchView.doOnQueryTextChanged(
    onTextChangedAction: (String) -> Unit
) {
    setOnQueryTextListener(
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    onTextChangedAction(newText)
                }
                return true
            }
        }
    )
}

fun ImageButton.setColorFilterPrimaryColor() {
    setColorFilter(
        MaterialColors.getColor(
            this,
            com.google.android.material.R.attr.colorPrimary
        ),
        PorterDuff.Mode.SRC_ATOP
    )
}
