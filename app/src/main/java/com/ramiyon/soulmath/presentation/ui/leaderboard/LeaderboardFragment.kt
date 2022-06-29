package com.ramiyon.soulmath.presentation.ui.leaderboard

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentLeaderboardBinding
import com.ramiyon.soulmath.presentation.adapter.LeaderboardAdapter
import com.ramiyon.soulmath.util.Resource
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeaderboardFragment : BaseFragment<FragmentLeaderboardBinding, LeaderboardViewModel>() {

    private val adapter by inject<LeaderboardAdapter>()
    override val viewModel: LeaderboardViewModel by viewModel()

    override fun inflateViewBinding(container: ViewGroup?): FragmentLeaderboardBinding =
        FragmentLeaderboardBinding.inflate(layoutInflater, container, false)

    override fun FragmentLeaderboardBinding.binder(): () -> Unit = {
        this.apply {
            rvLeaderboard.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = this@LeaderboardFragment.adapter
            }
            viewModel.fetchLeaderboard().observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Loading -> resourceLoading()
                    is Resource.Success -> resourceSuccess()
                    is Resource.Empty -> resourceEmpty()
                    is Resource.Error -> resourceError()
                }
            }
        }
    }

    override fun resourceLoading() {
        TODO("Not yet implemented")
    }

    override fun resourceSuccess() {
        TODO("Not yet implemented")
    }

    override fun resourceError() {
        TODO("Not yet implemented")
    }

    override fun resourceEmpty() {
        TODO("Not yet implemented")
    }
}