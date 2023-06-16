package com.example.dermadetect.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermadetect.R
import com.example.dermadetect.databinding.ActivitySummaryBinding

class SummaryActivity() : AppCompatActivity() {

    private lateinit var binding : ActivitySummaryBinding
    private lateinit var resultDisease : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedData = intent.getStringExtra("RESULT_DETECTION_DISEASE")
        binding.titlePenyakit.text = receivedData

        resultDisease = receivedData.toString()

        val result = when(receivedData){

            "Acne" -> acne()
            "Actinic Keratosis" -> actinicKeratosis()
            "Atopic Dermatitis" -> atopicDermatitis()
            "Eczema" -> eczema()
            "Light Disease" -> lightDisease()
            "Lupus" -> lupus()
            "Normal" -> normal()
            "Seborrheic keratoses" -> seborrheicKeratoses()
            "Tinea Ringworm" -> tineaRingworm()
            "Wartz Molluscum" -> wartzMolluscum()
            else -> "Penyakit ini diluar jangkauan kami, tolong segera hubungi dokter kulit."
        }
        binding.tvDeskripsiPenyakit.text = result

        binding.btnTips.setOnClickListener{
            tipsButton().toString()

        }
        binding.btnRekomendasi.setOnClickListener{
            recommendationButton()

        }
    }

    private fun tipsButton(){
        val data = resultDisease
        val intent = Intent(this@SummaryActivity, DailyTipsActivity::class.java)
        intent.putExtra("RESULT_DETECTION_DISEASE", data)
        startActivity(intent)

    }
    private fun recommendationButton(){

        val data = resultDisease
        val intent = Intent(this@SummaryActivity,ProductRecommendationActivity::class.java)
        intent.putExtra("RESULT_DETECTION_DISEASE", data)
        startActivity(intent)
    }

    private fun acne(): String {
        val items = arrayOf(
            getString(R.string.deskripsi_acne)
        )
        return items[0]
    }

    private fun actinicKeratosis(): String {
        val items = arrayOf(
            getString(R.string.deskripsi_actinic)
        )
        return items[0]
    }

    private fun atopicDermatitis(): String {
        val items = arrayOf(
            getString(R.string.deskripsi_atopic)
        )
        return items[0]
    }

    private fun eczema(): String {
        val items = arrayOf(
            getString(R.string.deskripsi_eczema)
        )
        return items[0]
    }

    private fun lightDisease(): String {
        val items = arrayOf(
            getString(R.string.deskripsi_light)
        )
        return items[0]
    }

    private fun lupus(): String {
        val items = arrayOf(
            getString(R.string.deskripsi_lupus)
        )
        return items[0]
    }
    private fun normal(): String {
        val items = arrayOf(
            getString(R.string.deskripsi_normal)
        )
        return items[0]
    }
    private fun seborrheicKeratoses(): String {
        val items = arrayOf(
            getString(R.string.deskripsi_seborrheic)
        )
        return items[0]
    }

    private fun tineaRingworm(): String {
        val items = arrayOf(
            getString(R.string.deskripsi_tinea)
        )
        return items[0]
    }

    private fun wartzMolluscum(): String {
        val items = arrayOf(
            getString(R.string.deskripsi_wartz)
        )
        return items[0]
    }
}