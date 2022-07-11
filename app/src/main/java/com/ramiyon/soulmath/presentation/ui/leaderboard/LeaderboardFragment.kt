package com.ramiyon.soulmath.presentation.ui.leaderboard

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gdsc.gdsctoast.GDSCToast.Companion.showAnyToast
import com.gdsc.gdsctoast.util.ToastShape
import com.gdsc.gdsctoast.util.ToastType
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.DialogRankBinding
import com.ramiyon.soulmath.databinding.FragmentLeaderboardBinding
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.presentation.adapter.LeaderboardAdapter
import com.ramiyon.soulmath.presentation.common.buildLeaderboardDialog
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeaderboardFragment : BaseFragment<FragmentLeaderboardBinding>() {

    private val adapter by inject<LeaderboardAdapter>()
    private val viewModel: LeaderboardViewModel by viewModel()
    private lateinit var dialogRankBinding: DialogRankBinding

    override fun inflateViewBinding(container: ViewGroup?): FragmentLeaderboardBinding =
        FragmentLeaderboardBinding.inflate(layoutInflater, container, false)

    override fun FragmentLeaderboardBinding.binder() {
        dialogRankBinding = DialogRankBinding.inflate(layoutInflater)
        rvLeaderboard.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@LeaderboardFragment.adapter
        }
        viewModel.fetchLeaderboard().observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> leaderboardResourceCallback.onResourceSuccess(it.data)
                is Resource.Loading -> leaderboardResourceCallback.onResourceLoading()
                is Resource.Empty -> leaderboardResourceCallback.onResourceEmpty()
                is Resource.Error -> leaderboardResourceCallback.onResourceError(it.message)
            }
        }
    }

    private val leaderboardResourceCallback = object : ResourceStateCallback<List<Leaderboard>?> {
        override fun onResourceLoading() {
            binding?.apply {
                holderTopThree.visibility = View.INVISIBLE
                progressBarTopThree.visibility = View.VISIBLE
                rvLeaderboard.visibility = View.INVISIBLE
                progressBarLeaderboard.visibility = View.VISIBLE
            }
        }

        override fun onResourceSuccess(data: List<Leaderboard>?) {
            binding?.apply {
                viewModel.fetchStudentRank().observe(viewLifecycleOwner) { rankResource ->
                    when(rankResource) {
                        is Resource.Success -> {
                            val dialog = requireContext().buildLeaderboardDialog(dialogRankBinding, rankResource.data)
                            ivLeaderboardInformation.setOnClickListener {
                                dialog.show()
                            }
                            progressBarTopThree.visibility = View.INVISIBLE

                            val topThree = data?.take(3)
                            val remains = data?.drop(3)
                            holderTopThree.apply {
                                visibility = View.VISIBLE
                                includeLeaderboardTopThree.apply {
                                    when(topThree?.size) {
                                        1 -> {
                                            val firstPlace = topThree[0]
                                            firstPlace.topThreeBind(ivAvatarFirstRank, tvUsernameFirstRank, tvXpFirstRank)
                                        }
                                        2 -> {
                                            val firstPlace = topThree[0]
                                            firstPlace.topThreeBind(ivAvatarFirstRank, tvUsernameFirstRank, tvXpFirstRank)
                                            val secondPlace = topThree[1]
                                            secondPlace.topThreeBind(ivAvatarSecondRank, tvUsernameSecondRank, tvXpSecondRank)
                                        }
                                        3 -> {
                                            val firstPlace = topThree[0]
                                            firstPlace.topThreeBind(ivAvatarFirstRank, tvUsernameFirstRank, tvXpFirstRank)
                                            val secondPlace = topThree[1]
                                            secondPlace.topThreeBind(ivAvatarSecondRank, tvUsernameSecondRank, tvXpSecondRank)
                                            val thirdPlace = topThree[2]
                                            thirdPlace.topThreeBind(ivAvatarThirdRank, tvUsernameThirdRank, tvXpThirdRank)
                                        }
                                    }
                                }
                            }
                            progressBarLeaderboard.visibility = View.INVISIBLE
                            rvLeaderboard.visibility = View.VISIBLE
                            remains?.let { list -> adapter.submitData(list) }
                        }
                        is Resource.Loading -> onResourceLoading()
                        is Resource.Empty -> return@observe
                        is Resource.Error -> onResourceError(rankResource.message)
                    }
                }
            }
        }

        override fun onResourceError(message: String?) {
            binding?.apply {
                requireContext().apply { showAnyToast { it.apply {
                    text = message.toString()
                    toastType = ToastType.ERROR
                    toastShape = ToastShape.RECTANGLE
                } } }
            }
        }

        override fun onResourceEmpty() {
            binding?.apply {
                progressBarTopThree.visibility = View.INVISIBLE
                progressBarLeaderboard.visibility = View.INVISIBLE
            }
        }
    }

    private fun Leaderboard?.topThreeBind(
        imageAvatar: ImageView,
        textUsername: TextView,
        textXp: TextView
    ) {
        Glide.with(requireContext()).load(this?.avatar).placeholder(R.drawable.ilu_default_profile_picture).into(imageAvatar)
        textUsername.text = this?.username
        textXp.text = getString(R.string.xp_earned, this?.xp ?: 0)
    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }
}