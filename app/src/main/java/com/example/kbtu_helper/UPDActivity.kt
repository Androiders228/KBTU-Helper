package com.example.kbtu_helper

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kbtu_helper.databinding.ActivityUpdBinding
import kotlinx.coroutines.launch

class UPDActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdBinding
    private lateinit var db: NotesDBHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        db = NotesDBHelper(this)
        noteId = intent.getIntExtra("noteId", -1)

        if (noteId == -1) {
            finish()
            return
        }

        // Fast loading task: Fetching a single note by ID is fast enough for the main thread
        val note = db.getNoteById(noteId)
        binding.updTitleEditText.setText(note.title)
        binding.updContentEditText.setText(note.content)

        binding.updSaveButton.setOnClickListener {
            val newTitle = binding.updTitleEditText.text.toString()
            val newContent = binding.updContentEditText.text.toString()
            val updatedNote = NoteData(noteId, newTitle, newContent)
            
            // Long loading task: Database updates should be async
            lifecycleScope.launch {
                db.updateNote(updatedNote)
                Toast.makeText(this@UPDActivity, "Отредактировано!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
