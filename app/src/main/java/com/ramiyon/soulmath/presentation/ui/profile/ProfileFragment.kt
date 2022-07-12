package com.ramiyon.soulmath.presentation.ui.profile

import android.view.ViewGroup
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentProfileBinding
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.androidx.navigation.koinNavGraphViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by koinNavGraphViewModel(R.id.mobile_navigation)

    override fun inflateViewBinding(container: ViewGroup?): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater, container, false)

    override fun FragmentProfileBinding.binder() {

    }

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT

}