package com.omarquintero.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val idNote: Int,
    val title: String,
    val description: String,
    val priority: Int
)