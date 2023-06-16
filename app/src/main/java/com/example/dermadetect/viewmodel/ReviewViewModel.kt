package com.example.dermadetect.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dermadetect.data.response.UpResponse
import com.example.dermadetect.data.retrofit.ApiConfig
import com.example.dermadetect.helper.Event
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    private val _isError = MutableLiveData<Event<Boolean>>()
    val isError: LiveData<Event<Boolean>> = _isError

    private val _isSuccess = MutableLiveData<Event<Boolean>>()
    val isSuccess: LiveData<Event<Boolean>> = _isSuccess

    fun uploadPhoto(
        image: MultipartBody.Part
    ) {
        _isLoading.value = Event(true)
        val service =
            ApiConfig.getApiService().uploadPicture(image)
        service.enqueue(object : Callback<UpResponse> {
            override fun onResponse(
                call: Call<UpResponse>,
                response: Response<UpResponse>
            ) {
                _isLoading.value = Event(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null ) {
                        _isSuccess.value = Event(true)
                    }
                } else {
                    _isError.value = Event(true)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UpResponse>, t: Throwable) {
                _isLoading.value = Event(false)
                _isError.value = Event(true)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "ReviewDetectViewModel"
    }
}