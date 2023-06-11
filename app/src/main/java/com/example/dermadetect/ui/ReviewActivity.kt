package com.example.dermadetect.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermadetect.R

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
    }
    companion object {
        const val CAMERA_RESULT = 10
        const val GALLERY_RESULT = 20

        private const val PATH_PICTURE = "path_picture"
        const val TOKEN_RESULT = "token_result"
        const val TOKEN_DETECTION = "token_detection"



    }
}