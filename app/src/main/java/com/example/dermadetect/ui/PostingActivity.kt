package com.example.dermadetect.ui

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.options
import com.example.dermadetect.R
import com.example.dermadetect.databinding.ActivityPostingBinding


class PostingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPostingBinding

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODE_PERMISSION) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.denied_access_permission),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
            if (allPermissionsGranted()) {
                startingCamera()
            }
        }
    }
    private fun allPermissionsGranted() = CAMERA_PERMISSION.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener{
            cameraPermission()
        }
        binding.btnGallery.setOnClickListener{
            startingGallery()

        }
    }

    private fun cameraPermission(){
        if(!allPermissionsGranted()){
            ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CODE_PERMISSION
            )
        }
        else{
            startingCamera()
        }
    }

    private fun startingCamera(){
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }

    private fun startingGallery(){
        imageCrop.launch(
            options {
                setImageSource(
                    includeCamera = false, includeGallery = true
                )
                setAspectRatio(aspectRatioX = 1, aspectRatioY = 1)
                setAllowRotation(allowRotation = false)
                setAllowFlipping(allowFlipping = false)
                setSkipEditing(skipEditing = true)
            }
        )
    }

    private val imageCrop = registerForActivityResult(CropImageContract()){result ->
        if (result.isSuccessful) {
            val uriImageCrop = result.uriContent

            val intent = Intent(this@PostingActivity, ReviewActivity::class.java)
            intent.putExtra(VALUE_IMAGE_URI, uriImageCrop)
            intent.putExtra(
                ReviewActivity.TOKEN_RESULT,
                ReviewActivity.GALLERY_RESULT
            )
            startActivity(intent)
        }

    }
    companion object{
        private val CAMERA_PERMISSION = arrayOf(android.Manifest.permission.CAMERA)
        private const val CODE_PERMISSION = 10
        const val VALUE_IMAGE_URI = "uri"

    }
}