package com.example.dermadetect.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dermadetect.R
import com.example.dermadetect.adapter.ProductRecomendationAdapter
import com.example.dermadetect.data.productRecom.DataProduct
import com.example.dermadetect.data.productRecom.Product
import com.example.dermadetect.databinding.ActivityProductRecomendationBinding

class ProductRecommendationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProductRecomendationBinding
    private lateinit var rvProduct : RecyclerView
    private var list : ArrayList<Product> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvProduct = findViewById(R.id.rv_product)
        rvProduct.setHasFixedSize(true)

        list.addAll(DataProduct.listProduct)
        rvProduct.layoutManager = LinearLayoutManager(this)
        val listProductAdapter = ProductRecomendationAdapter(list)
        rvProduct.adapter = listProductAdapter

    }
}