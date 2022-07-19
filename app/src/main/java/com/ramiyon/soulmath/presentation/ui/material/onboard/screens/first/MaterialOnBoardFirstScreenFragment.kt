package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.first

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramiyon.soulmath.R

class MaterialOnBoardFirstScreenFragment : Fragment() {

    companion object {
        fun newInstance() = MaterialOnBoardFirstScreenFragment()
    }

    private lateinit var viewModel: MaterialOnBoardFirstScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_material_on_board_first_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MaterialOnBoardFirstScreenViewModel::class.java)
    }

}