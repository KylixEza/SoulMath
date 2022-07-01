package com.ramiyon.soulmath.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseFragment<VB: ViewBinding>: Fragment() {

    private var _binding: VB? = null
    private val binding get() = _binding

    abstract fun inflateViewBinding(container: ViewGroup?): VB
    abstract fun VB.binder(): () -> Unit

    fun onCreateViewBehaviour(): () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateViewBinding(container)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            binder()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    abstract fun resourceLoading()
    abstract fun resourceSuccess()
    abstract fun resourceError()
    abstract fun resourceEmpty()

}