package com.example.notes.presenter.entities

sealed class NoteRecyclerItems{
    object ProgressItem : NoteRecyclerItems()
    data class NoteItem(val data: PresenterNoteEntity): NoteRecyclerItems()
}