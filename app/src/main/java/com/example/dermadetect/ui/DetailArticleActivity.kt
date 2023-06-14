package com.example.dermadetect.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dermadetect.R

class DetailArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        val btnBack : ImageView = findViewById(R.id.btn_back)
        btnBack.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val tvHeadline : TextView = findViewById(R.id.tv_title)
        val tvDescription : TextView = findViewById(R.id.tv_content)
        val tvDate : TextView = findViewById(R.id.tv_date)
        val tvAuthor : TextView = findViewById(R.id.tv_author)
        val imgDetail : ImageView = findViewById(R.id.iv_article)


        val headline = intent.getStringExtra(EXTRA_HEADLINE)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val date = intent.getStringExtra(EXTRA_DATE)
        val author = intent.getStringExtra(EXTRA_AUTHOR)
        val image = intent.getStringExtra(EXTRA_IMAGE)

        tvHeadline.text = headline
        tvDescription.text = description
        tvDate.text = date
        tvAuthor.text = author
        Glide.with(this)
            .load(image)
            .apply(RequestOptions())
            .into(imgDetail)
    }



    companion object{
        const val EXTRA_HEADLINE = "extra_headline"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_AUTHOR = "extra_author"
        const val EXTRA_IMAGE = "extra_image"
    }
}