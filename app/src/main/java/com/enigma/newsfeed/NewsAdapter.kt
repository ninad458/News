package com.enigma.newsfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter : RecyclerView.Adapter<NewsViewHolder>() {

    val newsCount get() = news.size

    private val news = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )

    override fun getItemCount() = news.count()

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindData(news[position])
    }

    fun setNews(articles: List<Article>) {
        news.clear()
        news.addAll(articles)
        notifyDataSetChanged()
    }

    fun addNews(articles: List<Article>) {
        val oldSize = news.size
        news.addAll(articles)
        notifyItemRangeChanged(oldSize, articles.size)
    }
}

class NewsViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
    fun bindData(article: Article) {
        itemView.apply {
            txt_title.text = article.title
            txt_description.text = article.description
            txt_source.text = article.source.name
            txt_news_date.text = article.publishedAt
            Glide.with(this).load(article.urlToImage).into(img_news)
        }
    }
}