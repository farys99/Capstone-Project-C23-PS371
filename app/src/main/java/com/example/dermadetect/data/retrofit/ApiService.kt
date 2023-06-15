package com.example.dermadetect.data.retrofit


import com.example.dermadetect.data.response.UpResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {  

    @Multipart
    @POST("/upload")
    fun uploadPicture(
        @Part file: MultipartBody.Part
    ): Call<UpResponse>
}