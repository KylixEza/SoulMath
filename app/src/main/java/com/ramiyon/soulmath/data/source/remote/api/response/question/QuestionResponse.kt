package com.ramiyon.soulmath.data.source.remote.api.response.question

import com.google.gson.annotations.SerializedName
import com.ramiyon.soulmath.data.source.remote.api.response.answer.AnswerResponse

data class QuestionResponse(
	@field:SerializedName("game_id")
	val gameId: String,
	
	@field:SerializedName("question_id")
	val questionId: String,
	
	@field:SerializedName("question")
	val question: String,
	
	@field:SerializedName("question_image")
	val questionImage: String?,
	
	@field:SerializedName("answers")
	val answers: List<AnswerResponse>
)
