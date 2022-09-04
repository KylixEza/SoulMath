package com.ramiyon.soulmath.presentation.ui.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentProfileBinding
import com.ramiyon.soulmath.presentation.adapter.DailyXpAdapter
import com.ramiyon.soulmath.presentation.adapter.ProfileAddOnAdapter
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ScreenOrientation
import com.ramiyon.soulmath.util.callGlide
import org.koin.android.ext.android.inject
import org.koin.androidx.navigation.koinNavGraphViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by koinNavGraphViewModel(R.id.mobile_navigation)
    private val dailyXpAdapter: DailyXpAdapter by inject()
    private val profileAddOnAdapter: ProfileAddOnAdapter by inject()

    override fun onCreateViewBehaviour(inflater: LayoutInflater, container: ViewGroup?) {
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        requireActivity().window.statusBarColor = resources.getColor(R.color.primary_700)
    }

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
                    includeStudentProfileCard.apply {
                        it.data?.avatar?.let { avatar -> requireContext().callGlide(avatar, ivAvatar, R.drawable.ilu_default_profile_picture) }
                        includeStudentProfileCard.apply {
                            tvUsername.text = it.data?.username
                            tvXpPoints.text = "${it.data?.xp.toString()} XP"
                        }
                    }
                }
            }
        }

        viewModel.getDailyXpList().observe(viewLifecycleOwner) { resource ->
            when(resource) {
                is Resource.Empty -> { }
                is Resource.Error -> { }
                is Resource.Loading -> { }
                is Resource.Success -> {
                    resource.data?.apply {
                        dailyXpAdapter.submitData(this)
                        val isNotTakenAlready = this.none { it.isTaken }
                        if (isNotTakenAlready) {
                            tvCheckInCountGreet.text = "Kamu belum check in lho hari ini, yuk check in"
                        } else {
                            val countDays = this.filter { it.isTaken }
                            tvCheckInCountGreet.text = "Selamat, kamu telah berhasil check in ${countDays.size} hari beruntun"
                        }
                    }

                }
            }
        }


        profileAddOnAdapter.submitData(viewModel.getProfileAddOns())
    }

    override fun determineScreenOrientation(): ScreenOrientation = ScreenOrientation.PORTRAIT

}