package com.ramiyon.soulmath.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdsc.gdsctoast.GDSCToast.Companion.showAnyToast
import com.gdsc.gdsctoast.util.ToastShape
import com.gdsc.gdsctoast.util.ToastType
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseFragment
import com.ramiyon.soulmath.databinding.FragmentHomeBinding
import com.ramiyon.soulmath.domain.model.DailyXp
import com.ramiyon.soulmath.domain.model.Student
import com.ramiyon.soulmath.domain.model.learning_journey.LearningJourney
import com.ramiyon.soulmath.presentation.adapter.LearningJourneyAdapter
import com.ramiyon.soulmath.util.Resource
import com.ramiyon.soulmath.util.ResourceStateCallback
import com.ramiyon.soulmath.util.ScreenOrientation
import org.koin.android.ext.android.inject
import org.koin.androidx.navigation.koinNavGraphViewModel
import kotlin.properties.Delegates

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by koinNavGraphViewModel(R.id.mobile_navigation)
    private var isTaken = false
    private val adapter: LearningJourneyAdapter by inject()

    override fun inflateViewBinding(container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun onCreateViewBehaviour(inflater: LayoutInflater, container: ViewGroup?) {
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        requireActivity().window.statusBarColor = resources.getColor(R.color.primary_700)
    }

    override fun FragmentHomeBinding.binder() {
        rvLearningJourney.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.fetchLearningJourney().observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Loading -> learningJourneyResourceCallback.onResourceLoading()
                is Resource.Success -> learningJourneyResourceCallback.onResourceSuccess(it.data!!)
                is Resource.Error -> learningJourneyResourceCallback.onResourceError(it.message, null)
                is Resource.Empty -> learningJourneyResourceCallback.onResourceEmpty()
            }
        }

        viewModel.getStudentDetail().observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Loading -> studentResourceCallback.onResourceLoading()
                is Resource.Success -> studentResourceCallback.onResourceSuccess(it.data!!)
                is Resource.Error -> studentResourceCallback.onResourceError(it.message, null)
                is Resource.Empty -> studentResourceCallback.onResourceEmpty()
            }
        }

        viewModel.getCurrentDailyXp().observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Loading -> dailyXpResourceCallback.onResourceLoading()
                is Resource.Success -> dailyXpResourceCallback.onResourceSuccess(it.data!!)
                is Resource.Error -> dailyXpResourceCallback.onResourceError(it.message, null)
                is Resource.Empty -> dailyXpResourceCallback.onResourceEmpty()
            }
        }

    }

    override fun determineScreenOrientation(): ScreenOrientation {
        return ScreenOrientation.PORTRAIT
    }

    private val studentResourceCallback = object : ResourceStateCallback<Student>() {
        override fun onResourceLoading() {
            binding?.apply {
                progressTvGreeting.visibility = visible
                progressTvStudentXp.visibility = visible
                tvGreeting.visibility = invisible
                tvStudentXp.visibility = invisible
            }
        }

        override fun onResourceSuccess(data: Student) {
            binding?.apply {
                progressTvGreeting.visibility = invisible
                progressTvStudentXp.visibility = invisible
                tvGreeting.apply {
                    visibility = visible
                    text = "Hi, ${data.username}"
                }
                tvStudentXp.apply {
                    visibility = visible
                    text = "${data.xp.toString()} XP"
                }
            }
        }

        override fun onResourceError(message: String?, data: Student?) {
            binding?.apply {
                requireContext().apply { showAnyToast { it.apply {
                    text = message.toString()
                    toastType = ToastType.ERROR
                    toastShape = ToastShape.RECTANGLE
                } } }
            }
        }
    }

    private val dailyXpResourceCallback = object : ResourceStateCallback<DailyXp>() {
        override fun onResourceLoading() {
            binding?.apply {
                progressIncludeTakeDailyXp.visibility = visible
                containerTakeDailyXp.visibility = invisible
            }
        }

        override fun onResourceSuccess(data: DailyXp) {
            binding?.apply {
                progressIncludeTakeDailyXp.visibility = invisible
                containerTakeDailyXp.visibility = visible
    
                viewModel.isTodayTaken().observe(viewLifecycleOwner) {
                    if (it is Resource.Success) {
                        isTaken = it.data!!
                        if(isTaken) {
                            viewModel.getTodayTakenXp().observe(viewLifecycleOwner) { resourceTodayTakenXp ->
                                if(resourceTodayTakenXp is Resource.Success) {
                                    includeTakeDailyXp.tvDailyBonusXp.text =
                                        resourceTodayTakenXp.data?.dailyXp.toString()
                                }
                            }
                            includeTakeDailyXp.tvTakeDailyXp.text = "Terkumpul"
                            isTaken = true
                        } else {
                            includeTakeDailyXp.tvTakeDailyXp.animation = AnimationUtils.loadAnimation(
                                requireContext(), R.anim.wiggle_animation
                            )
                            includeTakeDailyXp.tvDailyBonusXp.text = data.dailyXp.toString()
                            includeTakeDailyXp.tvTakeDailyXp.setOnClickListener {
                                if(!isTaken) {
                                    viewModel.takeDailyXp(data.dailyXpId).observe(viewLifecycleOwner) {
                                        when(it) {
                                            is Resource.Success -> {
                                                requireActivity().showAnyToast {
                                                    it.apply {
                                                        text = "XP hari ini berhasil diambil!"
                                                        toastType = ToastType.SUCCESS
                                                    }
                                                }
                                                includeTakeDailyXp.tvTakeDailyXp.text = "Terkumpul"
                                                isTaken = true
                                                includeTakeDailyXp.tvTakeDailyXp.clearAnimation()
                                            }
                                            else -> {}
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        override fun onResourceError(message: String?, data: DailyXp?) {
            binding?.apply {
                requireContext().apply { showAnyToast { it.apply {
                    text = message.toString()
                    toastType = ToastType.ERROR
                    toastShape = ToastShape.RECTANGLE
                } } }
            }
        }
    }

    private val learningJourneyResourceCallback = object : ResourceStateCallback<List<LearningJourney>>() {
        override fun onResourceLoading() {
            binding?.apply {
                progressRvLearningJourney.visibility = visible
                rvLearningJourney.visibility = invisible
            }
        }

        override fun onResourceSuccess(data: List<LearningJourney>) {
            binding?.apply {
                progressRvLearningJourney.visibility = invisible
                rvLearningJourney.visibility = visible
                adapter.submitData(data)
            }
        }

        override fun onResourceError(message: String?, data: List<LearningJourney>?) {
            binding?.apply {
                requireContext().apply { showAnyToast { it.apply {
                    text = message.toString()
                    toastType = ToastType.ERROR
                    toastShape = ToastShape.RECTANGLE
                } } }
            }
        }
    }
}