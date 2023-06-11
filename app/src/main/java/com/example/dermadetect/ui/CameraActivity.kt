package com.example.dermadetect.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Size
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.dermadetect.R
import com.example.dermadetect.databinding.ActivityCameraBinding
import com.example.dermadetect.util.createFile
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
            onBackPressed()
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
        startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        executorService.shutdown()
    }


    private fun takePictures(){
        val imageCapture = imageCapture ?: return
        val file = createFile(application)


        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(
            outputFileOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback{
                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        getString(R.string.unable_to_capture_photo),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val intent = Intent(this@CameraActivity, ReviewActivity::class.java)
                    intent.putExtra(ID_PICTURE, file)
                    intent.putExtra(
                        TOKEN_FRONT_CAMERA, cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA
                    )
                    intent.putExtra(
                        ReviewActivity.TOKEN_RESULT,
                        ReviewActivity.CAMERA_RESULT
                    )
                    startActivity(intent)
                    finish()
                }
            }
        )
    }
    private fun startCamera() {
        val processCameraProvider = ProcessCameraProvider.getInstance(this)

        processCameraProvider.addListener({
            val processCameraProviderTwo: ProcessCameraProvider = processCameraProvider.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewCamera.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().apply {
                setTargetResolution(Size(1000, 1000))
            }.build()

            try {
                processCameraProviderTwo.unbindAll()
                processCameraProviderTwo.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    getString(R.string.unable_to_start_the_camera),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    companion object{
        const val TOKEN_FRONT_CAMERA = "front_camera"
        const val ID_PICTURE = "picture"
    }

}