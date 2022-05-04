package com.ramiyon.soulmath.data.source.remote.api.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
	
	@field:SerializedName("uid")
	val uid: String,
	
	@field:SerializedName("address")
	val address: String,
	
	@field:SerializedName("avatar")
	val avatar: String,
	
	@field:SerializedName("foodie_wallet")
	val foodieWallet: Int,
	
	@field:SerializedName("email")
	val email: String,
	
	@field:SerializedName("name")
	val name: String,
	
	@field:SerializedName("phone_number")
	val phoneNumber: String,
	
	@field:SerializedName("xp")
	val xp: Int
)
