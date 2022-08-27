package com.ramiyon.soulmath.data.source.remote.api.response.student

import com.google.gson.annotations.SerializedName

data class StudentBody(
	@field:SerializedName("student_id")
	var studentId: String = "",

	@field:SerializedName("address")
	val address: String = "",

	@field:SerializedName("avatar")
	val avatar: String = "",

	@field:SerializedName("username")
	val username: String = "",

	@field:SerializedName("email")
	val email: String = "",

	@field:SerializedName("phone_number")
	val phoneNumber: String = "",

	@field:SerializedName("xp")
    var xp: Int = 0
)
