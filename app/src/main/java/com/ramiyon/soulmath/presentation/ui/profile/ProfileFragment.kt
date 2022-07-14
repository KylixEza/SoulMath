package com.ramiyon.soulmath.presentation.ui.profile

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentProfileBinding
import com.ramiyon.soulmath.presentation.adapter.DailyXpAdapter
import com.ramiyon.soulmath.presentation.adapter.ProfileAddOnAdapter
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModel()
    private val dailyXpAdapter: DailyXpAdapter by inject()
    private val profileAddOnAdapter: ProfileAddOnAdapter by inject()

    override fun inflateViewBinding(container: ViewGroup?): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater, container, false)

    @SuppressLint("SetTextI18n")
    override fun FragmentProfileBinding.binder() {

        rvCheckIn.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = dailyXpAdapter
        }

        rvProfileAddOns.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = profileAddOnAdapter
        }

        viewModel.getStudentDetail().observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Empty -> { }
                is Resource.Error -> { }
                is Resource.Loading -> { }
                is Resource.Success -> {
                    Glide.with(this@ProfileFragment).load(it.data?.avatar).into(this.includeStudentProfileCard.ivAvatar)
                    includeStudentProfileCard.apply {
                        tvUsername.text = it.data?.username
                        tvXpPoints.text = "${it.data?.xp.toString()} XP"
                    }
                }
            }
        }

        viewModel.getDailyXpList().observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Empty -> { }
                is Resource.Error -> { }
                is Resource.Loading -> { }
                is Resource.Success -> {
                    it.data?.let { it1 -> dailyXpAdapter.submitData(it1) }
                }
            }
        }


        profileAddOnAdapter.submitData(viewModel.getProfileAddOns())
    }

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT

}