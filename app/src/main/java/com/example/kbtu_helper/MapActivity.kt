package com.example.kbtu_helper

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kbtu_helper.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private var currentFloor = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Карта КБТУ"

        showFloor(1)
        setupFloorButtons()
    }

    private fun setupFloorButtons() {
        binding.btnFloor1.setOnClickListener { showFloor(1) }
        binding.btnFloor2.setOnClickListener { showFloor(2) }
        binding.btnFloor3.setOnClickListener { showFloor(3) }
        binding.btnFloor4.setOnClickListener { showFloor(4) }
    }

    private fun showFloor(floor: Int) {
        // Проверяем, что этаж другой
        if (currentFloor == floor) return

        val imageResource = when (floor) {
            1 -> R.drawable.floor_1
            2 -> R.drawable.floor_2
            3 -> R.drawable.floor_3
            4 -> R.drawable.floor_4
            else -> R.drawable.floor_1
        }

        try {
            binding.mapPhotoView.setImageResource(imageResource)
            currentFloor = floor

            updateButtonStates(floor)

            Toast.makeText(this, "$floor этаж", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Карта $floor этажа пока недоступна",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateButtonStates(selectedFloor: Int) {
        val buttons = listOf(
            binding.btnFloor1 to 1,
            binding.btnFloor2 to 2,
            binding.btnFloor3 to 3,
            binding.btnFloor4 to 4
        )

        buttons.forEach { (button, floor) ->
            if (floor == selectedFloor) {
               button.setBackgroundColor(Color.parseColor("#6200EE"))
                button.setTextColor(Color.WHITE)
            } else {

                button.setBackgroundColor(Color.WHITE)
                button.setTextColor(Color.parseColor("#6200EE"))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}