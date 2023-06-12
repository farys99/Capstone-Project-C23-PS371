package com.example.dermadetect.data.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @field:SerializedName("error")
    val error: String?= null,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("id")
    val id: String? = null
)