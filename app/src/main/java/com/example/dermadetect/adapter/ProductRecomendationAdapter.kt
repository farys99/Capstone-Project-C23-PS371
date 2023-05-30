package com.example.dermadetect.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dermadetect.R
import com.example.dermadetect.data.response.ProductList
import com.example.dermadetect.databinding.ItemProductRecomendationBinding

class ProductRecomendationAdapter : RecyclerView.Adapter<ProductRecomendationAdapter.ViewHolder>(){

    private val listProduct = ArrayList<ProductList>()

    @SuppressLint("NotifyDataSetChanged")
    fun setProductData(product : List<ProductList>) {
        listProduct.clear()
        listProduct.addAll(product)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = ItemProductRecomendationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(listProduct[position])
    }

    override fun getItemCount(): Int = listProduct.size

    class ViewHolder(private val view: ItemProductRecomendationBinding) : RecyclerView.ViewHolder(view.root){

        fun binding(listProduct: ProductList){
            with(view){
                headlineProduct.text = listProduct.name

                Glide.with(itemView.context)
                    .load(listProduct.photo)
                    .placeholder(R.drawable.baseline_insert_photo_24_2)
                    .into(imageProduct)
            }
        }

    }


}