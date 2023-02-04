package com.vlatrof.rickandmorty.presentation.common.listener

import android.os.SystemClock
import android.view.View

class DebouncedOnClickListener(
    private val delayMills: Long = DEFAULT_DELAY_MILLS,
    private val onClickAction: () -> Unit
) : View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(v: View) {
        val currentTime = SystemClock.uptimeMillis()
        if (lastClickTime == 0L || currentTime - lastClickTime > delayMills) {
            lastClickTime = currentTime
            onClickAction()
        }
    }

    companion object {
        const val DEFAULT_DELAY_MILLS = 1000L
    }
}
