package com.omarquintero.notes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_edit_note.*

class AddEditNote : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.omarquintero.notes.EXTRA_ID"
        const val EXTRA_TITLE = "com.omarquintero.notes.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.omarquintero.notes.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.omarquintero.notes.EXTRA_PRIORITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        npkPriority.minValue = 1
        npkPriority.maxValue = 10

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)

        //Change if Edit situation
        if(intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"
            edtTitle.setText(intent.getStringExtra(EXTRA_TITLE))
            edtDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            npkPriority.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        }else{
            title = "Add Note"
        }

    }

    fun saveNote(){
        val id = intent.getIntExtra(EXTRA_ID, -1)
        val title = edtTitle.text.toString()
        val description = edtDescription.text.toString()
        val priority = npkPriority.value
        val note: Note = Note(id, title, description, priority)

        if(note.title.trim().isEmpty() || note.description.trim().isEmpty()) {
            Toast.makeText(this, "Porfavor, llena los campos", Toast.LENGTH_SHORT).show()
            return
        }

        var data = Intent()
        data.putExtra(EXTRA_ID, note.idNote)
        data.putExtra(EXTRA_TITLE, note.title)
        data.putExtra(EXTRA_DESCRIPTION, note.description)
        data.putExtra(EXTRA_PRIORITY, note.priority)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    //MenuOptions
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu_add_note, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.btnSave -> {
                saveNote()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

}
