package com.example.dermadetect.data.response

import com.google.gson.annotations.SerializedName

data class UpResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)
