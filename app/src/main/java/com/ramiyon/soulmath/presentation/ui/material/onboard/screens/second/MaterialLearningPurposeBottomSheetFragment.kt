package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ramiyon.soulmath.databinding.FragmentMaterialLearningPurposeBottomSheetBinding

class MaterialLearningPurposeBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMaterialLearningPurposeBottomSheetBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMaterialLearningPurposeBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}