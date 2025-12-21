package com.example.kbtu_helper

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class NotesAdapter(private var notes: List<NoteData>, private val activity: NotesActivity) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDBHelper = NotesDBHelper(activity)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UPDActivity::class.java)
            intent.putExtra("noteId", note.id)
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            activity.lifecycleScope.launch {
                db.deleteNote(note.id)
                val newNotes = db.getAllNotes()
                refreshData(newNotes)
                Toast.makeText(holder.itemView.context, "Заметка удалена", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun refreshData(newNotes: List<NoteData>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updButton: ImageView = itemView.findViewById(R.id.updButton)

        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }
}
