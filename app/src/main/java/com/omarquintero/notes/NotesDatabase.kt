package com.omarquintero.notes

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(Note::class), version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object{
        private var instance: NotesDatabase? = null
        fun getInstance(application: Application): NotesDatabase{
            if (instance == null)
                instance = Room.databaseBuilder(application.applicationContext, NotesDatabase::class.java, "Note")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            return instance as NotesDatabase
        }

        //Populate the DB with a callback
        val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateNoteDbAsyncTask(instance!!).execute()
            }
        }
        private class PopulateNoteDbAsyncTask(noteDb: NotesDatabase): AsyncTask<Void, Void, Boolean>(){
            val noteDao: NoteDao = noteDb.noteDao()
            override fun doInBackground(vararg params: Void): Boolean {
                noteDao.insertNote(Note(1, "Tittle 1", "Description 1", 1))
                noteDao.insertNote(Note(2, "Tittle 2", "Description 2", 2))
                noteDao.insertNote(Note(3, "Tittle 3", "Description 3", 3))
                return true
            }
        }
    }
}