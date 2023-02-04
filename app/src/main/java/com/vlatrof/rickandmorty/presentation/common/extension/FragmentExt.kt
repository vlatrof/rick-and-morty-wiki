package com.vlatrof.rickandmorty.presentation.common.extension

import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.getDimen(@DimenRes resId: Int): Int =
    requireActivity().resources.getDimension(resId).toInt()

fun Fragment.showToast(@StringRes messageResId: Int, toastLength: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireActivity(), getString(messageResId), toastLength).show()
}
