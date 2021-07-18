package com.example.notes.presenter.recycler

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.base.BaseViewModel
import com.example.notes.domain.useCases.DelNoteUseCase
import com.example.notes.domain.useCases.GetNotesUseCase
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.entities.toPresentation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RecyclerViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val delNoteUseCase: DelNoteUseCase,
    private val coordinator: Coordinator
): BaseViewModel()  {

    private val mutableNoteList = MutableLiveData<List<NoteRecyclerHolder>>()
    val noteList: LiveData<List<NoteRecyclerHolder>> = mutableNoteList

    private val mutableSelectedMode = MutableLiveData<Boolean>()
    val selectedMode: LiveData<Boolean> = mutableSelectedMode

    init {
        disposable += getNotesUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { notes ->
                    Log.d("tag", "getNotes OK")
                    mutableNoteList.postValue(notes.map {
                        it.toPresentation()
                    })
                },
                {
                    Log.d("tag", "error on getNotes $it.toString()")
                }
            )
    }

    fun onAddNoteClick() {
        coordinator.openNoteEditor(NoteRecyclerHolder(0, "", "", "", ""))
    }

    fun onItemClick(noteRecyclerHolder: NoteRecyclerHolder) {
        coordinator.openNoteEditor(noteRecyclerHolder)
    }

    fun onLongTab() {
        mutableSelectedMode.postValue(true)
    }

    fun onNavigationBack() {
        mutableSelectedMode.postValue(false)
    }

    fun onDeleteClick(listNotes: List<NoteRecyclerHolder>) {
        disposable += delNoteUseCase(listNotes.map { it.id })
            .andThen(getNotesUseCase())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { notes ->
                    Log.d("tag", "getNotes OK")
                    mutableSelectedMode.postValue(false)
                    mutableNoteList.postValue(notes.map {
                        it.toPresentation()
                    })
                },
                {
                    Log.d("tag", "error on getNotes $it.toString()")
                    mutableSelectedMode.postValue(false)
                }
            )
    }
}