package com.vlatrof.rickandmorty.presentation.screen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.vlatrof.rickandmorty.presentation.common.navigation.NavigationManager

abstract class BaseDialogFragment<VB : ViewBinding>(
    @LayoutRes private val layoutRes: Int
) : DialogFragment(layoutRes) {

    private var _binding: VB? = null
    protected val binding
        get() = requireNotNull(_binding) {
            "Binding isn't init"
        }

    protected val navigationManager
        get() = requireActivity() as NavigationManager

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutRes, container, false)
        _binding = createBinding(view)
        return binding.root
    }

    abstract fun createBinding(view: View): VB
}
