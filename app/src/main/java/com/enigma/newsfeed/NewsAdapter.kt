package com.enigma.newsfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter : RecyclerView.Adapter<NewsViewHolder>() {

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
}

class NewsViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
    fun bindData(article: Article) {
        itemView.txt_title.text = article.title
    }
}