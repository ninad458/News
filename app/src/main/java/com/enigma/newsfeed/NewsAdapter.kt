package com.enigma.newsfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter : RecyclerView.Adapter<NewsViewHolder>() {

    companion object {
        private const val VT_NEWS = 1
        private const val VT_LOADER = 2
    }

    val newsCount get() = news.size

    var loading = false
        set(value) {
            field = value
            if (value) notifyItemInserted(news.size)
            else notifyItemRemoved(news.size)
        }

    private val news = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        when (viewType) {
            VT_NEWS -> NewsItem(parent)
            VT_LOADER -> LoaderItem(parent)
            else -> throw IllegalStateException("Unexpected view type")
        }

    override fun getItemViewType(position: Int) = if (loading && position == news.size)
        VT_LOADER else VT_NEWS

    override fun getItemCount() = news.count() + if (loading) 1 else 0

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        when (holder) {
            is NewsItem -> holder.bindData(news[position])
        }
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

fun ViewGroup.inflate(@LayoutRes resource: Int): View =
    LayoutInflater.from(context).inflate(resource, this, false)

sealed class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class LoaderItem(parent: ViewGroup) : NewsViewHolder(parent.inflate(R.layout.item_loading))

class NewsItem(parent: ViewGroup) : NewsViewHolder(parent.inflate(R.layout.item_news)) {
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