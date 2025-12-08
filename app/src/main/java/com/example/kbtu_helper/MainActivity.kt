package com.example.kbtu_helper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.kbtu_helper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        Thread.sleep(1000)
        installSplashScreen()

        setContentView(binding.root)


        binding.btnSchedule.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
        }

        binding.btnNotes.setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java))
        }

        binding.btnMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }
    }
}
