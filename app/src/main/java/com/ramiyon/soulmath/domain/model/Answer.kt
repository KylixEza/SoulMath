package com.ramiyon.soulmath.domain.model

import com.google.gson.annotations.SerializedName

data class Answer(
	
	@field:SerializedName("question_id")
	val questionId: String,
	
	@field:SerializedName("answer")
	val answer: String,
	
	@field:SerializedName("is_right_answer")
	val isRightAnswer: Boolean
	
)
