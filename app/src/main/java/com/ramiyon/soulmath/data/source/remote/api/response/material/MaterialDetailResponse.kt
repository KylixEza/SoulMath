package com.ramiyon.soulmath.data.source.remote.api.response.material

import com.google.gson.annotations.SerializedName

data class MaterialDetailResponse(
	@field:SerializedName("video_url")
	val videoUrl: String,
	
	@field:SerializedName("xp_earned")
	val xpEarned: Int,
	
	@field:SerializedName("is_favorite")
	val isFavorite: Boolean
)
