package com.ramiyon.soulmath.presentation.ui.material.onboard.screens.third

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramiyon.soulmath.R

class MaterialOnBoardThirdScreenFragment : Fragment() {

    companion object {
        fun newInstance() = MaterialOnBoardThirdScreenFragment()
    }

    private lateinit var viewModel: MaterialOnBoardThirdScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_material_on_board_third_screen, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MaterialOnBoardThirdScreenViewModel::class.java)
        // TODO: Use the ViewModel
    }

}