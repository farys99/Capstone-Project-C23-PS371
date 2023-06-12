package com.example.dermadetect.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dermadetect.data.response.UpResponse
import com.example.dermadetect.data.response.UploadResponse
import com.example.dermadetect.data.retrofit.ApiConfig
import com.example.dermadetect.helper.Event
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    private val _isFailed = MutableLiveData<Event<Boolean>>()
    val isFailed: LiveData<Event<Boolean>> = _isFailed

    private val _isUploadSuccess = MutableLiveData<Event<Boolean>>()
    val isUploadSuccess: LiveData<Event<Boolean>> = _isUploadSuccess

//    private val _idDetection = MutableLiveData<String?>()
//    val idDetection: LiveData<String?> = _idDetection

    fun uploadPhoto(
        image: MultipartBody.Part,
//        typeDetection: RequestBody
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
                    if (responseBody != null) {
                        _isUploadSuccess.value = Event(true)
//                        _idDetection.value = responseBody.statusCode
                    }
                } else {
                    _isFailed.value = Event(true)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UpResponse>, t: Throwable) {
                _isLoading.value = Event(false)
                _isFailed.value = Event(true)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "ReviewDetectViewModel"
    }
}