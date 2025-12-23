package com.example.kbtu_helper.notes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes_table")
    suspend fun getAllNotes(): List<NoteData>

    @Query("SELECT * FROM notes_table WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteData

    @Insert
    suspend fun insert(note: NoteData)

    @Update
    suspend fun update(note: NoteData)

    @Delete
    suspend fun delete(note: NoteData)
}