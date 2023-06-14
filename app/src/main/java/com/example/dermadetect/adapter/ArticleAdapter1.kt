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
import com.example.dermadetect.data.localArticle.Article
import com.example.dermadetect.ui.DetailArticleActivity

class ArticleAdapter1(private val listArticle1 : ArrayList<Article>): RecyclerView.Adapter<ArticleAdapter1.ListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_article_1, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val context = holder.itemView.context
        val args = listArticle1[position]

        Glide.with(context)
            .load(args.image)
            .into(holder.imgPhoto)
        holder.tvHeadline.text = args.headline
        holder.tvDate.text = args.date
        holder.tvAuthor.text = args.author

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailArticleActivity::class.java)
            intent.putExtra(DetailArticleActivity.EXTRA_HEADLINE, args.headline)
            intent.putExtra(DetailArticleActivity.EXTRA_DESCRIPTION, args.description)
            intent.putExtra(DetailArticleActivity.EXTRA_DATE, args.date)
            intent.putExtra(DetailArticleActivity.EXTRA_AUTHOR, args.author)
            intent.putExtra(DetailArticleActivity.EXTRA_IMAGE, args.image)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return listArticle1.size
    }

    class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val imgPhoto: ImageView = itemView.findViewById(R.id.image_article)
        val tvHeadline : TextView = itemView.findViewById(R.id.tv_title)
        val tvDate : TextView = itemView.findViewById(R.id.tv_date)
        val tvAuthor : TextView = itemView.findViewById(R.id.tv_author)
    }

}
