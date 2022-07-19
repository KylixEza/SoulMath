package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.second

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramiyon.soulmath.R

class MaterialOnBoardSecondScreenFragment : Fragment() {

    companion object {
        fun newInstance() = MaterialOnBoardSecondScreenFragment()
    }

    private lateinit var viewModel: MaterialOnBoardSecondScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_material_on_board_second_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MaterialOnBoardSecondScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

}