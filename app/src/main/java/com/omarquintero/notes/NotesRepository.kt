package com.omarquintero.notes

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class NotesRepository(application: Application) {
    private val noteDao: NoteDao
    private val notes: LiveData<List<Note>>

    init {
        val notesDatabase: NotesDatabase = NotesDatabase.getInstance(application)
        noteDao = notesDatabase.noteDao()
        notes = noteDao.listNotes()
    }

    fun listNotes(): LiveData<List<Note>>{
        return notes
    }
    fun insertNote(note: Note){
        InsertNoteAsyncTask(noteDao).execute(note)
    }
    fun updateNote(note: Note){
        UpdateNoteAsyncTask(noteDao).execute(note)
    }
    fun deleteNote(note: Note){
        DeleteNoteAsyncTask(noteDao).execute(note)
    }
    fun deleteAllNotes(){
        DeleteAllNotesAsyncTask(noteDao).execute()
    }

    companion object{
        private class InsertNoteAsyncTask(noteDao: NoteDao): AsyncTask<Note, Void, Boolean>(){
            val noteDao: NoteDao = noteDao
            override fun doInBackground(vararg params: Note): Boolean {
                noteDao.insertNote(params[0])
                return true
            }
        }
        private class UpdateNoteAsyncTask(noteDao: NoteDao): AsyncTask<Note, Void, Boolean>(){
            val noteDao: NoteDao = noteDao
            override fun doInBackground(vararg params: Note): Boolean {
                noteDao.updateNote(params[0])
                return true
            }
        }
        private class DeleteNoteAsyncTask(noteDao: NoteDao): AsyncTask<Note, Void, Boolean>(){
            val noteDao: NoteDao = noteDao
            override fun doInBackground(vararg params: Note): Boolean {
                noteDao.deleteNote(params[0])
                return true
            }
        }
        private class DeleteAllNotesAsyncTask(noteDao: NoteDao): AsyncTask<Void, Void, Boolean>(){
            val noteDao: NoteDao = noteDao
            override fun doInBackground(vararg params: Void): Boolean {
                noteDao.deleteAllNotes()
                return true
            }
        }
    }

}