package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.second

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ramiyon.soulmath.databinding.FragmentMaterialLearningPurposeBottomSheetBinding
import com.ramiyon.soulmath.presentation.adapter.LearningPurposeAdapter
import com.ramiyon.soulmath.util.Constanta.ARG_MATERIAL_ID
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MaterialLearningPurposeBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel by sharedViewModel<MaterialOnBoardSecondScreenViewModel>()
    private val adapter by inject<LearningPurposeAdapter>()
    private var _binding: FragmentMaterialLearningPurposeBottomSheetBinding? = null
    private val binding get() = _binding
    private var materialId: String? = null

    companion object {
        fun getInstance(materialId: String) = MaterialLearningPurposeBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_MATERIAL_ID, materialId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            materialId = it.getString(ARG_MATERIAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMaterialLearningPurposeBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val purposes = viewModel.getMaterialLearningPurposeById(materialId!!)
        binding?.apply {
            tvLearningPurposeChapter.text = "Tujuan Pembelajaran Bab ${purposes.chapter}"
            rvLearningPurpose.apply {
                adapter = this@MaterialLearningPurposeBottomSheetFragment.adapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            adapter.submitData(purposes.purposes)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}