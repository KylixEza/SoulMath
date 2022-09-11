package com.ramiyon.soulmath.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdsc.gdsctoast.GDSCToast.Companion.showAnyToast
import com.gdsc.gdsctoast.util.ToastShape
import com.gdsc.gdsctoast.util.ToastType
import com.google.android.material.snackbar.Snackbar
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
import io.github.tonnyl.light.Light.error
import io.github.tonnyl.light.Light.success
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.navigation.koinNavGraphViewModel

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
        
        viewModel.isTodayTaken().observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Loading -> isTakenResourceCallback.onResourceLoading()
                is Resource.Success -> isTakenResourceCallback.onResourceSuccess(it.data!!)
                is Resource.Error -> isTakenResourceCallback.onResourceError(it.message, null)
                is Resource.Empty -> isTakenResourceCallback.onResourceEmpty()
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.studentXp.collect {
                tvStudentXp.text = resources.getString(R.string.xp_earned, it)
            }
        }
        
        lifecycleScope.launchWhenStarted {
            viewModel.collectedText.collect {
                binding?.includeTakeDailyXp?.tvTakeDailyXp?.text = it
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
                tvStudentXp.visibility = visible
                viewModel.setStudentXp(data.xp)
            }
        }

        override fun onResourceError(message: String?, data: Student?) {
            error(binding?.root!!, message.toString(), Snackbar.LENGTH_SHORT)
        }
    }
    
    private val isTakenResourceCallback = object : ResourceStateCallback<Boolean>() {
        override fun onResourceLoading() {
            binding?.apply {
                progressIncludeTakeDailyXp.visibility = visible
                containerTakeDailyXp.visibility = invisible
            }
        }
    
        override fun onResourceSuccess(data: Boolean) {
            binding?.apply {
                isTaken = data
                progressIncludeTakeDailyXp.visibility = invisible
                containerTakeDailyXp.visibility = visible
                if(isTaken) {
                    observeTodayTakenXp()
                    viewModel.setCollectedText("Terkumpul")
                    isTaken = true
                } else {
                    viewModel.setCollectedText("Kumpulkan")
                    binding?.includeTakeDailyXp?.tvTakeDailyXp?.startAnimation(
                        AnimationUtils.loadAnimation(requireContext(), R.anim.wiggle_animation)
                    )
                    observeCurrentDailyXp()
                }
            }
        }
    
        override fun onResourceError(message: String?, data: Boolean?) {
            error(binding?.root!!, message.toString(), Snackbar.LENGTH_SHORT)
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
                error(binding?.root!!, message.toString(), Snackbar.LENGTH_SHORT)
            }
        }
    }
    
    private fun observeTodayTakenXp() {
        viewModel.getTodayTakenXp().observe(viewLifecycleOwner) { resourceTodayTakenXp ->
            if(resourceTodayTakenXp is Resource.Success) {
                binding?.includeTakeDailyXp?.tvDailyBonusXp?.text =
                    resourceTodayTakenXp.data?.dailyXp.toString()
            }
        }
    }
    
    private fun observeCurrentDailyXp() {
        viewModel.getCurrentDailyXp().observe(viewLifecycleOwner) { dailyXp ->
            when(dailyXp) {
                is Resource.Success -> {
                    binding?.apply {
                        includeTakeDailyXp.tvDailyBonusXp.text = dailyXp.data?.dailyXp.toString()
                        includeTakeDailyXp.tvTakeDailyXp.setOnClickListener {
                            if(!isTaken) {
                                observeTakeDailyXp(dailyXp)
                            }
                        }
                    }
                }
                else -> {}
            }
        }
    }
    
    private fun observeTakeDailyXp(dailyXp: Resource<DailyXp?>) {
        viewModel.takeDailyXp(dailyXp.data!!.dailyXpId).observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    success(requireView(), "XP hari ini berhasil diambil!", Snackbar.LENGTH_SHORT).show()
                    binding?.apply {
                        viewModel.setCollectedText("Terkumpul")
                        isTaken = true
                        includeTakeDailyXp.tvTakeDailyXp.clearAnimation()
                    }
                    viewModel.increaseStudentXp(dailyXp.data.dailyXp)
                }
                is Resource.Error -> {
                    error(binding?.root!!, it.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}