package com.example.dermadetect.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermadetect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.skinProblemsDetection.setOnClickListener{
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