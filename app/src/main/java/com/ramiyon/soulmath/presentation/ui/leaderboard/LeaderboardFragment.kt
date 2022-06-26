package com.ramiyon.soulmath.presentation.ui.leaderboard

import android.view.ViewGroup
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentLeaderboardBinding
import com.ramiyon.soulmath.presentation.adapter.LeaderboardAdapter
import org.koin.android.ext.android.inject

class LeaderboardFragment : BaseFragment<FragmentLeaderboardBinding, LeaderboardViewModel>() {

    private val adapter by inject<LeaderboardAdapter>()

    override fun inflateViewBinding(container: ViewGroup?): FragmentLeaderboardBinding =
        FragmentLeaderboardBinding.inflate(layoutInflater, container, false)

    override fun FragmentLeaderboardBinding.binder(): () -> Unit {
        TODO("Not yet implemented")
    }

}