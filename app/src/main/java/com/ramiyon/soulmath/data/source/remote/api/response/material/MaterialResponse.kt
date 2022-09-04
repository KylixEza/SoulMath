package com.ramiyon.soulmath.data.source.remote.api.response.material

import com.google.gson.annotations.SerializedName

data class MaterialResponse(
	
	@field:SerializedName("sub_module_id")
	val subModuleId: String,
	
	@field:SerializedName("material_id")
	val materialId: String,

	@field:SerializedName("material_image")
	val materialImage: String,

	@field:SerializedName("sub_module_title")
	val subModuleTitle: String,
	
	@field:SerializedName("video_url")
	val videoUrl: String,
	
	@field:SerializedName("xp_earned")
	val xpEarned: Int,
	
	@field:SerializedName("is_unlocked")
	val isUnlocked: Boolean
)
