package com.example.notes.presenter.recycler

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.base.BaseViewModel
import com.example.notes.domain.useCases.DelNoteUseCase
import com.example.notes.domain.useCases.GetNotesUseCase
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.coordinator.OnBackCollector
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.entities.toPresentation
import javax.inject.Inject

class RecyclerViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val delNoteUseCase: DelNoteUseCase,
    private val coordinator: Coordinator,
    private val onBackCollector: OnBackCollector
): BaseViewModel()  {
    private var isSelected = false

    private val mutableNoteList = MutableLiveData<List<NoteRecyclerHolder>>()
    val noteList: LiveData<List<NoteRecyclerHolder>> = mutableNoteList

    private val mutableSelectedMode = MutableLiveData<Boolean>()
    val selectedMode: LiveData<Boolean> = mutableSelectedMode

    override fun onCreate() {
        super.onCreate()
        onBackCollector.subscribe {
            if (isSelected) onNavigationBack()
            else coordinator.back()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackCollector.disposeLastSubscription()
    }

    override fun onResume() {
        getNotesUseCase().simpleSingleSubscribe { notes ->
            Log.d("tag", "getNotes OK")
            mutableNoteList.postValue(notes.map {
                it.toPresentation()
            })
        }
    }

    fun onAddNoteClick() {
        coordinator.openNoteEditor(NoteRecyclerHolder(
            id=0,
            header="",
            desc="",
            body="",
            image= listOf(),
            creationDate="",
            lastEditDate=""
        ))
    }

    fun onItemClick(noteRecyclerHolder: NoteRecyclerHolder) {
        coordinator.openNoteEditor(noteRecyclerHolder)
    }

    fun onLongTab() {
        isSelected = true
        mutableSelectedMode.postValue(true)
    }

    fun onNavigationBack() {
        isSelected = false
        mutableSelectedMode.postValue(false)
    }

    fun onDeleteClick(listNotes: List<NoteRecyclerHolder>) {
        isSelected = false
        mutableSelectedMode.postValue(false)
        delNoteUseCase(listNotes.map { it.id }).andThen(getNotesUseCase()).simpleSingleSubscribe { notes ->
            Log.d("tag", "getNotes OK")
            mutableNoteList.postValue(notes.map {
                it.toPresentation()
            })
        }
    }
}