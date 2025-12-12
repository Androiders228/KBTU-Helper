package com.example.kbtu_helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kbtu_helper.data.local.NewsArticle
import com.example.kbtu_helper.databinding.ItemNewsBinding

class NewsAdapter(
    private val onClick: (url: String) -> Unit
) : ListAdapter<NewsArticle, NewsAdapter.NewsViewHolder>(DiffCallback()) {

    class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<NewsArticle>() {
        override fun areItemsTheSame(a: NewsArticle, b: NewsArticle) = a.url == b.url
        override fun areContentsTheSame(a: NewsArticle, b: NewsArticle) = a == b
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.titleTextView.text = item.title ?: "No title"
        holder.binding.descriptionTextView.text = item.description ?: ""
        holder.binding.root.setOnClickListener {
            item.url.let { url -> if (!url.isNullOrBlank()) onClick(url) }
        }

        Glide.with(holder.itemView)
            .load(item.urlToImage)
            .centerCrop()
            .into(holder.binding.imageView)
    }
}
