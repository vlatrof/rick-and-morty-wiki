package com.vlatrof.rickandmorty.presentation.common.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GapItemDecoration(
    private val gapPx: Int,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.apply {
            val itemPosition = parent.getChildAdapterPosition(view)

            when (orientation) {
                GridLayoutManager.VERTICAL -> {
                    if (itemPosition < spanCount) top = gapPx
                    if (itemPosition % spanCount == 0) left = gapPx
                }
                GridLayoutManager.HORIZONTAL -> {
                    if (itemPosition < spanCount) left = gapPx
                    if (itemPosition % spanCount == 0) top = gapPx
                }
            }
            right = gapPx
            bottom = gapPx
        }
    }
}
