package com.example.dermadetect.data.retrofit

import com.example.dermadetect.data.response.ResultResponse
import com.example.dermadetect.data.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {  

    @Multipart
    @POST("/upload")
    fun uploadPicture(
        @Part file: MultipartBody.Part,
        @Part("kind_model") typeDetection: RequestBody
    ): Call<UploadResponse>

    @GET("/result")
    fun getResult(
        @Query("id") id: String?
    ): Call<ResultResponse>
}