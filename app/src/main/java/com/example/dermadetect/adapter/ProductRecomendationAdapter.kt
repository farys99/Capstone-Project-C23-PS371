package com.example.dermadetect.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dermadetect.R
import com.example.dermadetect.data.productRecom.Product

class ProductRecomendationAdapter(private val listProduct: ArrayList<Product>): RecyclerView.Adapter<ProductRecomendationAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductRecomendationAdapter.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ProductRecomendationAdapter.ListViewHolder,
        position: Int
    ) {
        val context = holder.itemView.context
        val args = listProduct[position]

        Glide.with(context)
            .load(args.image)
            .into(holder.imgPhoto)
        holder.tvTitle.text = args.namaProduct
        holder.tvDescription.text = args.descriptionProduct
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgPhoto : ImageView = itemView.findViewById(R.id.iv_product)
        val tvTitle : TextView = itemView.findViewById(R.id.title_product)
        val tvDescription : TextView = itemView.findViewById(R.id.content_product)

    }


}