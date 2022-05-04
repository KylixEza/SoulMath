package com.ramiyon.soulmath.data.source.remote.api.response

import com.google.gson.annotations.SerializedName

data class UserBody(
	@field:SerializedName("uid")
	var uid: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("avatar")
	val avatar: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("phone_number")
	val phoneNumber: String,
)
