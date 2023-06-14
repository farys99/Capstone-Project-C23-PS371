package com.example.dermadetect.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dermadetect.R
import com.example.dermadetect.adapter.ArticleAdapter
import com.example.dermadetect.adapter.ArticleAdapter1
import com.example.dermadetect.data.localArticle.Article
import com.example.dermadetect.data.localArticle.DataArticle
import com.example.dermadetect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvArticle : RecyclerView
    private lateinit var rvArticle2 : RecyclerView
    private var list : ArrayList<Article> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvArticle = findViewById(R.id.rv_article)
        rvArticle.setHasFixedSize(true)

        list.addAll(DataArticle.listData)
        rvArticle.layoutManager = LinearLayoutManager(this)
        val listArticleAdapter = ArticleAdapter(list)
        rvArticle.adapter = listArticleAdapter


        rvArticle2 = findViewById(R.id.rv_article2)
        rvArticle2.setHasFixedSize(true)

        rvArticle2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listArticleAdapter1 = ArticleAdapter1(list)
        rvArticle2.adapter = listArticleAdapter1


        binding.cardButton.setOnClickListener{
            skinDetectionProblem(SKIN_PROBLEM)

            val intent = Intent(this, PostingActivity::class.java)
            startActivity(intent)
        }



    }
    private fun skinDetectionProblem(skin : String){
        val setting = getSharedPreferences(MENU_SKIN_PROBLEM, Context.MODE_PRIVATE)
        val edit = setting.edit()
        edit.putString(KEY_SKIN_PROBLEM, skin)
        edit.apply()
    }

    companion object{
        const val MENU_SKIN_PROBLEM = "menu_skin_problem"
        const val SKIN_PROBLEM = "skin_problem"
        const val KEY_SKIN_PROBLEM = "key_skin_problem"
    }

}