package com.vlatrof.rickandmorty.presentation.common.listener

import android.os.SystemClock
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnScrollToEndListener(
    private val debounceDelayMillis: Long = DEFAULT_DELAY_MILLIS,
    private val layoutManager: LinearLayoutManager,
    private val onScrolledToEndAction: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var totalItemCount = 0
    private var visibleItemCount = 0
    private var firstVisibleItemPos = 0
    private var lastScrollToEndTime = 0L

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val currentTime = SystemClock.uptimeMillis()

        if (dy > 0) {
            visibleItemCount = layoutManager.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition()

            if ((visibleItemCount + firstVisibleItemPos) >= totalItemCount) {
                if (lastScrollToEndTime == 0L || currentTime - lastScrollToEndTime >
                    debounceDelayMillis
                ) {
                    lastScrollToEndTime = currentTime
                    onScrolledToEndAction()
                }
            }
        }
    }

    companion object {
        const val DEFAULT_DELAY_MILLIS = 800L
    }
}
