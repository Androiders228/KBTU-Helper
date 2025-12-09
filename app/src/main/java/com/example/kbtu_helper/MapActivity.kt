package com.example.kbtu_helper

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.kbtu_helper.databinding.ActivityMapBinding
import com.google.android.material.button.MaterialButton

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private var currentFloor = 0 // Начинаем с 0, чтобы гарантированно загрузить первый этаж

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFloorButtons()
        showFloor(1)
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

        val imageResource = when (floor) {
            1 -> R.drawable.floor_1
            2 -> R.drawable.floor_2
            3 -> R.drawable.floor_3
            4 -> R.drawable.floor_4
            5 -> R.drawable.floor_5
            else -> return
        }

        try {
            binding.mapPhotoView.setImageResource(imageResource)
            currentFloor = floor
            updateButtonStates(floor) // Обновляем внешний вид кнопок
            // Toast.makeText(this, "$floor этаж", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Карта для $floor этажа не найдена", Toast.LENGTH_SHORT).show()
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
