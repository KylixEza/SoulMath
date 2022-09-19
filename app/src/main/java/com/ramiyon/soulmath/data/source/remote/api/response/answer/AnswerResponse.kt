package com.ramiyon.soulmath.data.source.remote.api.response.answer

import com.google.gson.annotations.SerializedName

data class AnswerResponse(
	
	@field:SerializedName("question_id")
	val questionId: String,
	
	@field:SerializedName("answer")
	val answer: String,
	
	@field:SerializedName("is_right_answer")
	val isRightAnswer: Boolean
	
)
