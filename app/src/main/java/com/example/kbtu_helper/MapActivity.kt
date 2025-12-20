package com.example.kbtu_helper

import android.content.Context // Added
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.kbtu_helper.databinding.ActivityMapBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private var currentFloor = 0

    private val PREFS_NAME = "KBTU_PREFS"
    private val KEY_LAST_FLOOR = "last_floor"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFloorButtons()

        val savedFloor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_LAST_FLOOR, 1)

        showFloor(savedFloor)
    }

    private fun setupFloorButtons() {
        binding.btnFloor1.setOnClickListener { showFloor(1) }
        binding.btnFloor2.setOnClickListener { showFloor(2) }
        binding.btnFloor3.setOnClickListener { showFloor(3) }
        binding.btnFloor4.setOnClickListener { showFloor(4) }
        binding.btnFloor5.setOnClickListener { showFloor(5) }
    }

    private fun showFloor(floor: Int) {
        if (currentFloor == floor) return

        lifecycleScope.launch {
            try {
                val imageResource = loadFloorImage(floor)
                binding.mapPhotoView.setImageResource(imageResource)
                currentFloor = floor
                updateButtonStates(floor)

                saveFloorToPreferences(floor)

            } catch (e: Exception) {
                Toast.makeText(this@MapActivity, "Карта для $floor этажа не найдена", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveFloorToPreferences(floor: Int) {
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt(KEY_LAST_FLOOR, floor)
            apply()
        }
    }

    private suspend fun loadFloorImage(floor: Int): Int = withContext(Dispatchers.Default) {
        when (floor) {
            1 -> R.drawable.floor_1
            2 -> R.drawable.floor_2
            3 -> R.drawable.floor_3
            4 -> R.drawable.floor_4
            5 -> R.drawable.floor_5
            else -> throw IllegalArgumentException("Нет такого этажа")
        }
    }

    private fun updateButtonStates(selectedFloor: Int) {
        val buttons = mapOf(
            1 to binding.btnFloor1,
            2 to binding.btnFloor2,
            3 to binding.btnFloor3,
            4 to binding.btnFloor4,
            5 to binding.btnFloor5
        )

        val activeColor = ContextCompat.getColor(this, R.color.md_theme_primary)
        val inactiveColor = ContextCompat.getColor(this, R.color.md_theme_surfaceContainerHighest)
        val activeTextColor = ContextCompat.getColor(this, R.color.md_theme_onPrimary)
        val inactiveTextColor = ContextCompat.getColor(this, R.color.md_theme_onSurface)

        buttons.forEach { (floor, button) ->
            if (floor == selectedFloor) {
                button.backgroundTintList = ColorStateList.valueOf(activeColor)
                button.setTextColor(activeTextColor)
            } else {
                button.backgroundTintList = ColorStateList.valueOf(inactiveColor)
                button.setTextColor(inactiveTextColor)
            }
        }
    }
}