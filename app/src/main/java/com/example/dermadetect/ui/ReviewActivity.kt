package com.example.dermadetect.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.dermadetect.R
import com.example.dermadetect.databinding.ActivityReviewBinding
import com.example.dermadetect.util.rotateBitmap
import com.example.dermadetect.util.rotateFileImage
import com.example.dermadetect.util.uriToFile
import com.example.dermadetect.viewmodel.ReviewViewModel
import java.io.File

class ReviewActivity : AppCompatActivity() {

    private lateinit var reviewViewModel: ReviewViewModel
    private lateinit var binding: ActivityReviewBinding
    private var globalFile: File?=null
    private var alertDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewViewModel = ViewModelProvider(this)[ReviewViewModel::class.java]

        setPreviewImage()

        binding.btnProcess.setOnClickListener{
            displayNotificationUploadSuccess()
        }
        binding.btnTryAgain.setOnClickListener{
            onBackPressed()
        }
    }

    private fun fetchImageGallery(){
        val chosenImage = intent.getParcelableExtra<Uri>("uri") as Uri

        val localFile = uriToFile(chosenImage, this)
        globalFile = localFile

        binding. ivImage.setImageURI(chosenImage)
    }
    private fun fetchImageCamera(){
        val localFile = intent.getSerializableExtra(CameraActivity.ID_PICTURE) as File
        val isFrontCamera = intent.getBooleanExtra(CameraActivity.TOKEN_FRONT_CAMERA, true)

        val imageResult = rotateBitmap(
            BitmapFactory.decodeFile(localFile.path),
            isFrontCamera
        )
        globalFile = rotateFileImage(localFile, isFrontCamera)
        binding.ivImage.setImageBitmap(imageResult)
    }

    private fun displayNotificationUploadSuccess(){
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.title_alertDialog))
            setMessage(getString(R.string.message_alertDialog))
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                uploadPhoto()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
            create()
            show()
        }
    }

    private fun uploadPhoto(){}

    private fun setPreviewImage(){
        val result = intent.getIntExtra(TOKEN_RESULT, 0)
        if (result == GALLERY_RESULT){
            fetchImageGallery()
        }
        else if (result == CAMERA_RESULT){
            fetchImageCamera()
        }
    }

    companion object {
        const val CAMERA_RESULT = 10
        const val GALLERY_RESULT = 20

        private const val PATH_PICTURE = "path_picture"
        const val TOKEN_RESULT = "token_result"
        const val TOKEN_DETECTION = "token_detection"



    }
}