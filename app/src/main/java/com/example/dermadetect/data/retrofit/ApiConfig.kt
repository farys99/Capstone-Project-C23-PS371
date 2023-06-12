package com.example.dermadetect.data.retrofit

import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }

        private const val URL_BASE = "http://104.197.16.252/"
    }
}