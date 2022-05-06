package com.ramiyon.soulmath.presentation.ui.onboard.screens

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.databinding.FragmentSecondScreenBinding
import com.ramiyon.soulmath.databinding.LottieDialogBinding
import com.ramiyon.soulmath.presentation.common.buildLottieDialog

class SecondScreenFragment : Fragment() {

    private val binding by viewBinding<FragmentSecondScreenBinding>()
    private lateinit var lottieBinding: LottieDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        lottieBinding = LottieDialogBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_second_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            activity?.apply {
                val lottieDialog = buildLottieDialog(lottieBinding, "loading_blue_paper_airplane.json")
                btnNext.setOnClickListener {
                    lottieDialog.show()
                    Handler(mainLooper).postDelayed({
                        lottieDialog.dismiss()
                    }, 10000L)
                }
            }
        }

    }
}