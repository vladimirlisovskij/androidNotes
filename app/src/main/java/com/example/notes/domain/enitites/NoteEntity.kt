package com.example.notes.domain.enitites

data class NoteEntity (
    var id: Long,
    val header: String,
    val desc: String,
    val body: String,
    val image: List<String>,
    val creationDate: String,
    val lastEditDate: String
)