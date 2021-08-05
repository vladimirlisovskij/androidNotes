package com.example.notes.cleanArchitecture.domain.enitites

data class NoteEntity (
    var id: String,
    val header: String,
    val desc: String,
    val body: String,
    val image: List<String>,
    val creationDate: String,
    val lastEditDate: String
)

data class WidgetNoteEntity (
    var id: Long,
    val header: String,
    val desc: String,
    val body: String
)