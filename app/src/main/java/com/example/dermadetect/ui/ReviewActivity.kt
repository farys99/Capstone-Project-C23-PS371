package com.example.dermadetect.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.dermadetect.R
import com.example.dermadetect.databinding.ActivityReviewBinding
import com.example.dermadetect.util.rotateBitmap
import com.example.dermadetect.util.rotateFileImage
import com.example.dermadetect.util.uriToFile
import com.example.dermadetect.viewmodel.ReviewViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ReviewActivity : AppCompatActivity() {

    private lateinit var reviewViewModel: ReviewViewModel
    private lateinit var binding: ActivityReviewBinding
    private var globalFile: File?=null
    private var alertDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

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
            setPositiveButton(getString(R.string.ya)) { _, _ ->
                uploadPhoto()
            }
            setNegativeButton(getString(R.string.tidak)) { dialog, _ -> dialog.cancel() }
            create()
            show()
        }
    }

    private fun getDetectionFromMain():String?{
        val sharedPreferences = getSharedPreferences(MainActivity.MENU_SKIN_PROBLEM, Context.MODE_PRIVATE)
        return sharedPreferences.getString(MainActivity.KEY_SKIN_PROBLEM, null)
    }

    private fun uploadPhoto(){
        val item = globalFile as File
        val mainDetection = getDetectionFromMain() as String

        val sliceMainDetection = mainDetection.toRequestBody("text/plain".toMediaType())
        val reqImageFile = item.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multiPartBody : MultipartBody.Part = MultipartBody.Part.createFormData(
            PATH_PICTURE,
            item.name,
            reqImageFile
        )

        reviewViewModel.uploadPhoto(multiPartBody)
        reviewViewModel.isLoading.observe(this@ReviewActivity){
            it.getContentIfNotHandled()?.let {
                result -> isLoading(result)
            }
        }
        reviewViewModel.isError.observe(this@ReviewActivity){
            it.getContentIfNotHandled()?.let {
                    isError()
            }
        }
        reviewViewModel.isSuccess.observe(this@ReviewActivity){
            it.getContentIfNotHandled()?.let {
                /**do something**/
                isSuccess("1")
            }
        }
    }

    private fun isLoading(loading : Boolean){
        if(loading){
            val dialog = AlertDialog.Builder(this)
            dialog.apply {
                setView(
                    View.inflate(
                        this@ReviewActivity,R.layout.layout_progressbar, null
                    )
                )
                setCancelable(false)
            }
            alertDialog = dialog.create()
            alertDialog!!.show()
        }else{
            alertDialog?.dismiss()
        }
    }

    private fun isError(){
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.title_isError))
            setMessage(getString(R.string.message_isError))
            setPositiveButton(getString(R.string.reload)) { _, _ ->
                uploadPhoto()
            }
            create()
            show()
        }
    }

    private fun isSuccess(keyDetection:String?){
        val intent = Intent(this@ReviewActivity, SummaryActivity::class.java)
        intent.putExtra(TOKEN_DETECTION, keyDetection)
        startActivity(intent)
        finish()
    }

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