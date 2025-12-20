package com.example.kbtu_helper

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kbtu_helper.data.NewsRepository
import com.example.kbtu_helper.data.local.NewsDatabase
import com.example.kbtu_helper.databinding.ActivityNewsBinding
import com.example.kbtu_helper.ui.NewsViewModel
import com.example.kbtu_helper.ui.NewsViewModelFactory

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private val API_KEY = "03fe8f34745f4586990b26ea3a3414ed"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = NewsDatabase.getDatabase(this).newsDao()
        val repository = NewsRepository(API_KEY, dao)
        viewModel = ViewModelProvider(this, NewsViewModelFactory(repository))[NewsViewModel::class.java]

        adapter = NewsAdapter { articleUrl ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl))
            startActivity(intent)
        }

        binding.recyclerNews.layoutManager = LinearLayoutManager(this)
        binding.recyclerNews.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadNews()
        }

        viewModel.news.observe(this) { list ->
            adapter.submitList(list)
            binding.emptyTextView.alpha = if (list.isEmpty()) 1f else 0f
        }
        viewModel.loading.observe(this) { loading ->
            binding.swipeRefresh.isRefreshing = loading
        }
        viewModel.error.observe(this) { err ->
            binding.emptyTextView.text = err ?: "No news available"
        }

        viewModel.loadNews()
    }
}
