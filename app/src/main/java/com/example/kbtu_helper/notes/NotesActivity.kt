package com.example.kbtu_helper.notes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kbtu_helper.databinding.ActivityNotesBinding
import kotlinx.coroutines.launch

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var db: NotesDatabase
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabase.getDatabase(this)

        notesAdapter = NotesAdapter(emptyList(), this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this@NotesActivity, AddNotesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val notes = db.notesDao().getAllNotes()
            notesAdapter.refreshData(notes)
        }
    }
}