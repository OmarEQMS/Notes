package com.omarquintero.notes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY priority DESC")
    fun listNotes(): LiveData<List<Note>>
    @Insert
    fun insertNote(note: Note)
    @Update
    fun updateNote(note: Note)
    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM Note")
    fun deleteAllNotes()
}