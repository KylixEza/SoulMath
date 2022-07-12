package com.ramiyon.soulmath.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ramiyon.soulmath.presentation.validator.ConstraintValidator
import com.ramiyon.soulmath.util.ScreenOrientation

abstract class BaseFragment<VB: ViewBinding>: Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding

    abstract fun inflateViewBinding(container: ViewGroup?): VB
    abstract fun VB.binder()
    abstract fun determineScreenOrientation(): ScreenOrientation

    open fun onCreateViewBehaviour(inflater: LayoutInflater, container: ViewGroup?) { }
    open fun onViewCreatedBehaviour() { }
    open fun constraintValidator(): ConstraintValidator? { return null }
    open fun onDestroyBehaviour() { }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewBehaviour(inflater, container)
        if(_binding == null) {
            _binding = inflateViewBinding(container)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(determineScreenOrientation() == ScreenOrientation.PORTRAIT) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        binding?.apply {
            binder()
            constraintValidator()?.validate()
        }
    }

    override fun onDestroyView() {
       super.onDestroyView()
        onDestroyBehaviour()
        _binding = null
    }

}