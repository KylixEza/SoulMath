package com.ramiyon.soulmath.presentation.ui.leaderboard

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.bumptech.glide.Glide
import com.gdsc.gdsctoast.GDSCToast.Companion.showAnyToast
import com.gdsc.gdsctoast.util.ToastShape
import com.gdsc.gdsctoast.util.ToastType
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.data.worker.LeaderboardWorker
import com.ramiyon.soulmath.databinding.DialogRankBinding
import com.ramiyon.soulmath.databinding.FragmentLeaderboardBinding
import com.ramiyon.soulmath.domain.model.Leaderboard
import com.ramiyon.soulmath.presentation.adapter.LeaderboardAdapter
import com.ramiyon.soulmath.presentation.common.buildLeaderboardDialog
import com.ramiyon.soulmath.presentation.common.setLeaderboardDialogData
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.navigation.koinNavGraphViewModel
import java.util.concurrent.TimeUnit

class LeaderboardFragment : BaseFragment<FragmentLeaderboardBinding>() {

    private val adapter by inject<LeaderboardAdapter>()
    private val viewModel: LeaderboardViewModel by koinNavGraphViewModel(R.id.mobile_navigation)
    private lateinit var dialogRankBinding: DialogRankBinding
    private lateinit var dialog: Dialog

    override fun onCreateViewBehaviour(inflater: LayoutInflater, container: ViewGroup?) {
        requireActivity().window.statusBarColor = resources.getColor(R.color.primary_700)
    }

    override fun inflateViewBinding(container: ViewGroup?): FragmentLeaderboardBinding =
        FragmentLeaderboardBinding.inflate(layoutInflater, container, false)

    override fun FragmentLeaderboardBinding.binder() {
        dialogRankBinding = DialogRankBinding.inflate(layoutInflater)
        rvLeaderboard.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = this@LeaderboardFragment.adapter
        }

        refresh.setProgressBackgroundColor(R.color.primary_700)
        refresh.setColorSchemeResources(R.color.white)
        dialog = requireContext().buildLeaderboardDialog(viewLifecycleOwner ,dialogRankBinding, null)
        ivLeaderboardInformation.setOnClickListener {
            dialog.show()
        }
        
        viewModel.fetchLeaderboard(false).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> leaderboardResourceCallback.onResourceLoading()
                is Resource.Success -> leaderboardResourceCallback.onResourceSuccess(it.data)
                is Resource.Error -> leaderboardResourceCallback.onResourceError(it.message, it.data)
                is Resource.Empty -> leaderboardResourceCallback.onResourceEmpty()
            }
        }

        refresh.setOnRefreshListener {
            viewModel.fetchLeaderboard(true).observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> leaderboardResourceCallback.onResourceLoading()

                    is Resource.Success -> {
                        leaderboardResourceCallback.onResourceSuccess(it.data)
                        refresh.isRefreshing = false
                    }
                    is Resource.Error -> leaderboardResourceCallback.onResourceError(it.message, it.data)
                    is Resource.Empty -> leaderboardResourceCallback.onResourceEmpty()
                }
            }
        }
    }

    private val leaderboardResourceCallback = object : ResourceStateCallback<List<Leaderboard>?>() {
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
    
                viewModel.fetchStudentRank().observe(viewLifecycleOwner) { rankResource ->
                    when(rankResource) {
                        is Resource.Success -> setLeaderboardDialogData(rankResource.data)
                        is Resource.Loading ->  { }
                        is Resource.Empty -> return@observe
                        is Resource.Error -> { }
                    }
                }
            }
        }

        override fun onResourceError(message: String?, data: List<Leaderboard>?) {
            binding?.apply {
                requireContext().apply { showAnyToast { it.apply {
                    text = message.toString()
                    toastType = ToastType.ERROR
                    toastShape = ToastShape.RECTANGLE
                } } }
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