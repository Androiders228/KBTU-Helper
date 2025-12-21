package com.example.kbtu_helper.notes

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kbtu_helper.databinding.ActivityAddNotesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var dbHelper: NotesDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = NotesDBHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()

            if (title.isNotEmpty() || content.isNotEmpty()) {
                val note = NoteData(0, title, content)

                lifecycleScope.launch(Dispatchers.IO) {
                    dbHelper.insertNote(note)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddNotesActivity, "Заметка добавлена!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Заметка не может быть пустой:/", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
