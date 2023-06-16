package com.example.dermadetect.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dermadetect.R
import com.example.dermadetect.databinding.ActivityDailyTipsBinding
import com.example.dermadetect.databinding.ActivitySummaryBinding

class DailyTipsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDailyTipsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedData = intent.getStringExtra("RESULT_DETECTION_DISEASE")
        binding.titlePenyakit.text = receivedData

        val penyebab = when(receivedData){
            "Acne" -> getString(R.string.penyebab_acne)
            "Actinic Keratosis" -> getString(R.string.penyebab_actinic)
            "Atopic Dermatitis" -> getString(R.string.penyebab_atopic)
            "Eczema" -> getString(R.string.penyebab_eczema)
            "Light Disease" -> getString(R.string.penyebab_light)
            "Lupus" -> getString(R.string.penyebab_lupus)
            "Normal" -> getString(R.string.kulit_normal)
            "Seborrheic keratoses" -> getString(R.string.penyebab_seborrheic)
            "Tinea Ringworm" -> getString(R.string.penyebab_tinea)
            "Wartz Molluscum" -> getString(R.string.penyebab_wartz)
            else -> "Penyakit ini diluar jangkauan kami, tolong segera hubungi dokter kulit mengetahui penyebabnya."
        }
        binding.penyebabPenyakit.text = penyebab

        val mengobati = when(receivedData){
            "Acne" -> getString(R.string.pengobatan_acne)
            "Actinic Keratosis" -> getString(R.string.pengobatan_actinic)
            "Atopic Dermatitis" -> getString(R.string.pengobatan_atopic)
            "Eczema" -> getString(R.string.pengobatan_eczema)
            "Light Disease" -> getString(R.string.pengobatan_light)
            "Lupus" -> getString(R.string.pengobatan_lupus)
            "Normal" -> getString(R.string.kulit_normal)
            "Seborrheic keratoses" -> getString(R.string.pengobatan_seborrheic)
            "Tinea Ringworm" -> getString(R.string.pengobatan_tinea)
            "Wartz Molluscum" -> getString(R.string.pengobatan_wartz)
            else -> "Penyakit ini diluar jangkauan kami, tolong segera hubungi dokter kulit mengetahui pengobatannya."
        }
        binding.mengobatiPenyakit.text = mengobati

        val mencegah = when(receivedData){
            "Acne" -> getString(R.string.pencegahan_acne)
            "Actinic Keratosis" -> getString(R.string.pencegahan_actinic)
            "Atopic Dermatitis" -> getString(R.string.pencegahan_atopic)
            "Eczema" -> getString(R.string.pencegahan_eczema)
            "Light Disease" -> getString(R.string.pencegahan_light)
            "Lupus" -> getString(R.string.pencegahan_lupus)
            "Normal" -> getString(R.string.kulit_normal)
            "Seborrheic keratoses" -> getString(R.string.pencegahan_seborrheic)
            "Tinea Ringworm" -> getString(R.string.pencegahan_tinea)
            "Wartz Molluscum" -> getString(R.string.pencegahan_wartz)
            else -> "Penyakit ini diluar jangkauan kami, tolong segera hubungi dokter kulit mengetahui pencegahannya."
        }
        binding.mencegahPenyakit.text = mencegah



    }
}