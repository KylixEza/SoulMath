package com.ramiyon.soulmath.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.ramiyon.soulmath.R
import com.ramiyon.soulmath.base.BaseRecyclerViewAdapter
import com.ramiyon.soulmath.databinding.ItemListLearningJourneyBinding
import com.ramiyon.soulmath.domain.model.learning_journey.LearningJourney
import com.ramiyon.soulmath.presentation.diff_callback.LearningJourneyDiffUtil
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

            pbMaterial.progress = item.materialLearningJourneyResponse.materialPercentage.toInt()



            firstSubModule.apply {
                Glide.with(itemView!!.context).load(item.moduleIconLocked).into(ivSubModuleIcon)
                val firstSubModule = item.gameLearningJourneyResponses[0]
                apbSubModule.progress = firstSubModule.gamePercentage.toInt()
                if(firstSubModule.isDifficultiesCrowned.easy)
                    Glide.with(itemView!!.context).load(R.drawable.ic_crowned).into(ivSubModuleFirstCrown)
                else
                    Glide.with(itemView!!.context).load(R.drawable.ic_uncrowned).into(ivSubModuleFirstCrown)

                if(firstSubModule.isDifficultiesCrowned.medium)
                    Glide.with(itemView!!.context).load(R.drawable.ic_crowned).into(ivSubModuleSecondCrown)
                else
                    Glide.with(itemView!!.context).load(R.drawable.ic_uncrowned).into(ivSubModuleSecondCrown)

                if(firstSubModule.isDifficultiesCrowned.hard)
                    Glide.with(itemView!!.context).load(R.drawable.ic_crowned).into(ivSubModuleThirdCrown)
                else
                    Glide.with(itemView!!.context).load(R.drawable.ic_uncrowned).into(ivSubModuleThirdCrown)
            }

            secondSubModule.apply {
                Glide.with(itemView!!.context).load(item.moduleIconLocked).into(ivSubModuleIcon)
                val secondSubModule = item.gameLearningJourneyResponses[1]
                apbSubModule.progress = secondSubModule.gamePercentage.toInt()
                if(secondSubModule.isDifficultiesCrowned.easy)
                    Glide.with(itemView!!.context).load(R.drawable.ic_crowned).into(ivSubModuleFirstCrown)
                else
                    Glide.with(itemView!!.context).load(R.drawable.ic_uncrowned).into(ivSubModuleFirstCrown)

                if(secondSubModule.isDifficultiesCrowned.medium)
                    Glide.with(itemView!!.context).load(R.drawable.ic_crowned).into(ivSubModuleSecondCrown)
                else
                    Glide.with(itemView!!.context).load(R.drawable.ic_uncrowned).into(ivSubModuleSecondCrown)

                if(secondSubModule.isDifficultiesCrowned.hard)
                    Glide.with(itemView!!.context).load(R.drawable.ic_crowned).into(ivSubModuleThirdCrown)
                else
                    Glide.with(itemView!!.context).load(R.drawable.ic_uncrowned).into(ivSubModuleThirdCrown)
            }

            thirdSubModule.apply {
                Glide.with(itemView!!.context).load(item.moduleIconLocked).into(ivSubModuleIcon)
                val thirdSubModule = item.gameLearningJourneyResponses[2]
                apbSubModule.progress = thirdSubModule.gamePercentage.toInt()
                if(thirdSubModule.isDifficultiesCrowned.easy)
                    Glide.with(itemView!!.context).load(R.drawable.ic_crowned).into(ivSubModuleFirstCrown)
                else
                    Glide.with(itemView!!.context).load(R.drawable.ic_uncrowned).into(ivSubModuleFirstCrown)

                if(thirdSubModule.isDifficultiesCrowned.medium)
                    Glide.with(itemView!!.context).load(R.drawable.ic_crowned).into(ivSubModuleSecondCrown)
                else
                    Glide.with(itemView!!.context).load(R.drawable.ic_uncrowned).into(ivSubModuleSecondCrown)

                if(thirdSubModule.isDifficultiesCrowned.hard)
                    Glide.with(itemView!!.context).load(R.drawable.ic_crowned).into(ivSubModuleThirdCrown)
                else
                    Glide.with(itemView!!.context).load(R.drawable.ic_uncrowned).into(ivSubModuleThirdCrown)
            }
        }
    }

    override val diffUtilBuilder: (List<LearningJourney>, List<LearningJourney>) -> DiffUtil.Callback = { old, new ->
        LearningJourneyDiffUtil(old, new)
    }
}