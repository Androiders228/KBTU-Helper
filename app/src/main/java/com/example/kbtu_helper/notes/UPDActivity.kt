package com.example.kbtu_helper.notes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kbtu_helper.databinding.ActivityUpdBinding
import kotlinx.coroutines.launch

class UPDActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdBinding
    private lateinit var db: NotesDatabase
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        db = NotesDatabase.getDatabase(this)
        noteId = intent.getIntExtra("noteId", -1)

        if (noteId == -1) {
            finish()
            return
        }

        lifecycleScope.launch {
            val note = db.notesDao().getNoteById(noteId)
            binding.updTitleEditText.setText(note.title)
            binding.updContentEditText.setText(note.content)
        }

        binding.updSaveButton.setOnClickListener {
            val newTitle = binding.updTitleEditText.text.toString()
            val newContent = binding.updContentEditText.text.toString()
            val updatedNote = NoteData(noteId, newTitle, newContent)
            
            lifecycleScope.launch {
                db.notesDao().update(updatedNote)
                Toast.makeText(this@UPDActivity, "Отредактировано!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}