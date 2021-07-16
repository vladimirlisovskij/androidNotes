package com.example.notes.presenter.noteEdit

import androidx.lifecycle.LiveData
import com.example.notes.base.BaseViewModel
import com.example.notes.view.entities.NoteRecyclerHolder
import io.reactivex.rxjava3.core.Completable

interface NoteViewModel: BaseViewModel {
    fun onApplyClick(noteRecyclerHolder: NoteRecyclerHolder)
    fun onDelClick(id: Int)
}
