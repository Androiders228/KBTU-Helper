package com.example.kbtu_helper

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.kbtu_helper.databinding.ActivityUpdBinding

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

        val note = db.getNoteById(noteId)
        binding.updTitleEditText.setText(note.title)
        binding.updContentEditText.setText(note.content)

        binding.updSaveButton.setOnClickListener {
            val newTitle = binding.updTitleEditText.text.toString()
            val newContent = binding.updContentEditText.text.toString()
            val updatedNote = NoteData(noteId, newTitle, newContent)
            db.updateNote(updatedNote)
            finish()
            Toast.makeText(this, "Отредактировано!", Toast.LENGTH_SHORT).show()
        }


    }
}
