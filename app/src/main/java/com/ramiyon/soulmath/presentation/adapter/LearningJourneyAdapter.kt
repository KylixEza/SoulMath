package com.ramiyon.soulmath.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseRecyclerViewAdapter
import com.ramiyon.soulmath.databinding.IncludeSubModuleProgressBarBinding
import com.ramiyon.soulmath.databinding.ItemListLearningJourneyBinding
import com.ramiyon.soulmath.domain.model.learning_journey.LearningJourney
import com.ramiyon.soulmath.presentation.diff_callback.LearningJourneyDiffUtil
import com.ramiyon.soulmath.presentation.ui.home.HomeFragmentDirections
import com.ramiyon.soulmath.util.callGlide

class LearningJourneyAdapter: BaseRecyclerViewAdapter<ItemListLearningJourneyBinding, LearningJourney>() {
    override fun inflateViewBinding(parent: ViewGroup): ItemListLearningJourneyBinding {
        return ItemListLearningJourneyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override val binder: (LearningJourney, ItemListLearningJourneyBinding) -> Unit = { item, binding ->
        binding.apply {
            callGlide(itemView!!.context, item.moduleImage, ivModuleHeader)
            if(item.isModuleUnlocked)
                callGlide(itemView!!.context, item.moduleIconUnlocked, ivModuleIcon)
            else
                callGlide(itemView!!.context, item.moduleIconUnlocked, ivModuleIcon)
            
            ivModuleIcon.setOnClickListener {
                if(item.isModuleUnlocked)
                    it.findNavController().navigate(HomeFragmentDirections.actionHomeDestinationToMaterialOnBoardActivity(
                        item.materialLearningJourneyResponse.currentMaterialId,
                        item.moduleId
                    ))
            }

            pbMaterial.progress = item.materialLearningJourneyResponse.materialPercentage.toInt()

            firstSubModule.apply {
                buildSubModuleProgressBar(item, 0)
            }

            secondSubModule.apply {
               buildSubModuleProgressBar(item, 1)
            }

            thirdSubModule.apply {
                buildSubModuleProgressBar(item, 2)
            }
        }
    }

    override val diffUtilBuilder: (List<LearningJourney>, List<LearningJourney>) -> DiffUtil.Callback = { old, new ->
        LearningJourneyDiffUtil(old, new)
    }

    private fun IncludeSubModuleProgressBarBinding.buildSubModuleProgressBar(item: LearningJourney, position: Int) {
        this.apply {
            val subModuleByPosition = item.gameLearningJourneyResponses[position]
            if (subModuleByPosition.isGameUnlocked) {
                callGlide(itemView!!.context, item.moduleIconUnlocked, ivSubModuleIcon)
            } else {
                callGlide(itemView!!.context, item.moduleIconLocked, ivSubModuleIcon)
            }
            apbSubModule.progress = subModuleByPosition.gamePercentage.toInt()
            crowningSubModule { subModuleByPosition.isDifficultiesCrowned.easy }
            crowningSubModule { subModuleByPosition.isDifficultiesCrowned.medium }
            crowningSubModule { subModuleByPosition.isDifficultiesCrowned.hard }
        }
    }

    private fun IncludeSubModuleProgressBarBinding.crowningSubModule(condition: () -> Boolean) {
        if(condition())
            callGlide(itemView!!.context, R.drawable.ic_crowned, ivSubModuleFirstCrown)
        else
            callGlide(itemView!!.context, R.drawable.ic_uncrowned, ivSubModuleFirstCrown)
    }
}