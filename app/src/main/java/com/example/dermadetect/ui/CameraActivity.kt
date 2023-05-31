package com.example.dermadetect.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import com.example.dermadetect.R
import com.example.dermadetect.databinding.ActivityCameraBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var executorService: ExecutorService

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()

        binding.captureButton.setOnClickListener {
            takePictures()
        }
        binding.backButton.setOnClickListener {
            /**do something**/
        }
        binding.switchButton.setOnClickListener {
            cameraSelector = if(cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
            else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }

    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onResume() {
        super.onResume()
        /**do something**/
    }

    override fun onDestroy() {
        super.onDestroy()
        executorService.shutdown()
    }


    private fun takePictures(){
        /**do something takePhoto**/

    }
    private fun startCamera(){

    }

    companion object{
        const val FRONT_CAMERA = "front_camera"
        const val PICTURE = "picture"
    }

}