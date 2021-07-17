package com.example.notes.presenter.recycler

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.base.BaseViewModel
import com.example.notes.domain.useCases.GetNotesUseCase
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.entities.toPresentation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RecyclerViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val coordinator: Coordinator
): BaseViewModel()  {
    private val mutableNoteList = MutableLiveData<List<NoteRecyclerHolder>>()
    val noteList: LiveData<List<NoteRecyclerHolder>> = mutableNoteList

    init {
        disposable += getNotesUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { notes ->
                    Log.d("tag", "get OK")
                    mutableNoteList.postValue(notes.map {
                        it.toPresentation()
                    })
                },
                {
                    Log.d("tag", "error $it.toString()")
                }
            )
    }

    fun onAddNoteClick() {
        coordinator.openNoteEditor(NoteRecyclerHolder(0, "", "", "", "123"))
    }

    fun onItemClick(noteRecyclerHolder: NoteRecyclerHolder) {
        coordinator.openNoteEditor(noteRecyclerHolder)
    }
}