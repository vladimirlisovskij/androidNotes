package com.example.notes.presenter.entities

import androidx.annotation.LayoutRes
import com.example.notes.R

sealed class NoteRecyclerItems(
    @LayoutRes val layoutId: Int
) {
    class ProgressItem: NoteRecyclerItems(R.layout.item_circle_progress)
    data class NoteItem(val data: PresenterNoteEntity): NoteRecyclerItems(R.layout.item_recycler_note)
}