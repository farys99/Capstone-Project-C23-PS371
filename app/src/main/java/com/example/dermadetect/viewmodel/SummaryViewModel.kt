package com.example.dermadetect.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dermadetect.data.response.ResultResponse
import com.example.dermadetect.data.retrofit.ApiConfig
import com.example.dermadetect.helper.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SummaryViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    private val _isFailed = MutableLiveData<Event<Boolean>>()
    val isFailed: LiveData<Event<Boolean>> = _isFailed

    private val _summaryDetect = MutableLiveData<ResultResponse>()
    val summaryDetect: LiveData<ResultResponse> = _summaryDetect

//    fun uploadPhoto(
//        idDetection: String?,
//    ) {
//        _isLoading.value = Event(true)
//        val service =
//            ApiConfig.getApiService().getResult(idDetection)
//        service.enqueue(object : Callback<ResultResponse> {
//            override fun onResponse(
//                call: Call<ResultResponse>,
//                response: Response<ResultResponse>
//            ) {
//                _isLoading.value = Event(false)
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    if (responseBody != null && !responseBody.error) {
//                        _summaryDetect.value = response.body()
//                    }
//                } else {
//                    _isFailed.value = Event(true)
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
//                _isLoading.value = Event(false)
//                _isFailed.value = Event(true)
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
//    }

    companion object {
        private const val TAG = "ResultViewModel"
    }
}