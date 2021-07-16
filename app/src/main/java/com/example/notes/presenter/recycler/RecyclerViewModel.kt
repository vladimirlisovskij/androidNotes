package com.example.notes.presenter.recycler

import androidx.lifecycle.LiveData
import com.example.notes.base.BaseViewModel
import com.example.notes.view.entities.NoteRecyclerHolder

interface RecyclerViewModel: BaseViewModel {
    val noteList: LiveData<List<NoteRecyclerHolder>>

    fun onAddNoteClick()
    fun onItemClick(noteRecyclerHolder: NoteRecyclerHolder)
}
