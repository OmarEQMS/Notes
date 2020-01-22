package com.omarquintero.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NotesViewModel(application: Application): AndroidViewModel(application) {
    private val notesRepository: NotesRepository = NotesRepository(application)
    private val notes: LiveData<List<Note>>

    init{
        notes = notesRepository.listNotes()
    }

    fun listNotes(): LiveData<List<Note>>{
        return notes
    }
    fun insertNote(note: Note){
        notesRepository.insertNote(note)
    }
    fun updateNote(note: Note){
        notesRepository.updateNote(note)
    }
    fun deleteNote(note: Note){
        notesRepository.deleteNote(note)
    }
    fun deleteAllNotes(){
        notesRepository.deleteAllNotes()
    }

}