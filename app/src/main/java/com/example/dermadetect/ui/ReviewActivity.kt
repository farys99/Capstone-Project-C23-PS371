package com.example.dermadetect.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.dermadetect.R
import com.example.dermadetect.databinding.ActivityReviewBinding
import com.example.dermadetect.ml.Modelhampirfix
import com.example.dermadetect.viewmodel.ReviewViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File

class ReviewActivity : AppCompatActivity() {

    private lateinit var reviewViewModel: ReviewViewModel
    private lateinit var binding: ActivityReviewBinding
    private var globalFile: File?=null
    private var alertDialog: AlertDialog? = null
    lateinit var bitmap: Bitmap
    lateinit var imageView: ImageView
    private lateinit var resultPredict : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewViewModel = ViewModelProvider(this)[ReviewViewModel::class.java]

        setPreviewImage()

        binding.btnProcess.setOnClickListener{
            lastButtonPredict()

        }
        binding.btnTryAgain.setOnClickListener{
            onBackPressed()
        }
        resultPredict = findViewById(R.id.resultPredict)
        imageView = findViewById(R.id.iv_image_review)

    }

    private fun prediction(){
        var tensorImage = TensorImage(DataType. FLOAT32)
        tensorImage.load(bitmap)

        var imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp( 224,224, ResizeOp. ResizeMethod. BILINEAR))
            .build()

        var labels = application.assets.open("bangkitModel.txt"). bufferedReader(). readLines()

        tensorImage = imageProcessor.process(tensorImage)

        val model = Modelhampirfix.newInstance(this)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        var maxIdx = 0
        outputFeature0.forEachIndexed { index, fl ->
            if(outputFeature0[maxIdx]<fl){
                maxIdx = index
            }
        }

        resultPredict.setText(labels[maxIdx])
        model.close()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            var uri = data?.data;
            bitmap = MediaStore. Images.Media.getBitmap(this. contentResolver, uri)
            imageView.setImageBitmap(bitmap)
        }

    }

    private fun fetchImageGallery(){

        var intent = Intent()
        intent.setAction(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent,100)
    }
    private fun fetchImageCamera(){
        var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, 100) }
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
        reviewViewModel.isSuccess.observe(this@ReviewActivity){
            it.getContentIfNotHandled()?.let {
                /**do something**/
                isSuccess("")
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

    private fun isSuccess(disease:String?){
        val intent = Intent(this@ReviewActivity, SummaryActivity::class.java)
        //intent.putExtra(TOKEN_DETECTION, keyDetection)
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

    private fun lastButtonPredict(){
        prediction()
        val data = resultPredict.text
        val intent = Intent(this@ReviewActivity, SummaryActivity::class.java)
        intent.putExtra("RESULT_DETECTION_DISEASE", data)
        startActivity(intent)
    }

    companion object {
        const val CAMERA_RESULT = 10
        const val GALLERY_RESULT = 20

        private const val PATH_PICTURE = "path_picture"
        const val TOKEN_RESULT = "token_result"



    }
}