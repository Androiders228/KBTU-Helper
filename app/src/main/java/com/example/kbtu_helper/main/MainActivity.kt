package com.example.kbtu_helper.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.kbtu_helper.databinding.ActivityMainBinding
import com.example.kbtu_helper.map.MapActivity
import com.example.kbtu_helper.news.NewsActivity
import com.example.kbtu_helper.notes.NotesActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen: SplashScreen = installSplashScreen()

        var keepSplash = true
        splashScreen.setKeepOnScreenCondition { keepSplash }

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(800)
            keepSplash = false
        }

        binding.titleTextView


        binding.cardViewMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }
        binding.cardViewNews.setOnClickListener {
            startActivity(Intent(this, NewsActivity::class.java))
        }
        binding.cardViewNotes.setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java))
        }
    }
}