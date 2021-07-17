package com.example.notes.domain.enitites

data class NoteEntity (
    var id: Int,
    val header: String,
    val desc: String,
    val body: String,
    val image: String
)