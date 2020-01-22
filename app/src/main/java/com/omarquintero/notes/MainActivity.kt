package com.omarquintero.notes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val ADD_NOTE_REQUEST = 1
        const val EDIT_NOTE_REQUEST = 2
    }

    private lateinit var noteViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Adapter
        var noteAdapter = NoteAdapter()
        rcvNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        //Listener
        noteAdapter.setOnItemClickListener(object: NoteAdapter.OnItemClickListener{
            override fun onItemClick(note: Note) {
                var intent = Intent(this@MainActivity, AddEditNote::class.java)
                intent.putExtra(AddEditNote.EXTRA_ID, note.idNote)
                intent.putExtra(AddEditNote.EXTRA_TITLE, note.title)
                intent.putExtra(AddEditNote.EXTRA_DESCRIPTION, note.description)
                intent.putExtra(AddEditNote.EXTRA_PRIORITY, note.priority)
                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }
        })

        //ViewModel LiveData
        noteViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        noteViewModel.listNotes().observe(this, Observer { notes ->
            noteAdapter.setNotes(notes)
            //Toast.makeText(this@MainActivity, "OnChanged", Toast.LENGTH_SHORT).show()
        })

        //Table
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.deleteNote(noteAdapter.getItemAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note Deleted", Toast.LENGTH_SHORT).show()
            }
            override fun onMove(recyclerView: RecyclerView,viewHolder: RecyclerView.ViewHolder,target: RecyclerView.ViewHolder): Boolean { return false }
        }).attachToRecyclerView(rcvNotes)

        //btnAdd
        btnAdd.setOnClickListener{ view ->
            var intent = Intent(this@MainActivity, AddEditNote::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.btnDeleteAll -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK ){
            when(requestCode){
                ADD_NOTE_REQUEST -> {
                    val title = data!!.getStringExtra(AddEditNote.EXTRA_TITLE)
                    val description = data.getStringExtra(AddEditNote.EXTRA_DESCRIPTION)
                    val priority = data.getIntExtra(AddEditNote.EXTRA_PRIORITY, 0)
                    val note = Note(0, title, description, priority)
                    noteViewModel.insertNote(note)
                    Toast.makeText(this, "Note Inserted", Toast.LENGTH_SHORT).show()
                }
                EDIT_NOTE_REQUEST -> {
                    var id = data!!.getIntExtra(AddEditNote.EXTRA_ID, -1)
                    val title = data!!.getStringExtra(AddEditNote.EXTRA_TITLE)
                    val description = data.getStringExtra(AddEditNote.EXTRA_DESCRIPTION)
                    val priority = data.getIntExtra(AddEditNote.EXTRA_PRIORITY, 0)
                    val note = Note(id, title, description, priority)

                    if(note.idNote!=-1) {
                        noteViewModel.updateNote(note)
                        Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

}
