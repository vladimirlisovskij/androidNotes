package com.example.notes.presenter.noteEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.base.BaseViewModel
import com.example.notes.presenter.coordinator.OnBackCollector
import com.example.notes.presenter.entities.NoteRecyclerHolder
import javax.inject.Inject

class NoteViewModel @Inject constructor(): BaseViewModel()  {
    private val _hideKeyboard = MutableLiveData<Unit>()
    val hideKeyboard get() = _hideKeyboard as LiveData<Unit>

    private val _result = MutableLiveData<NoteRecyclerHolder>()
    val result get() = _result as LiveData<NoteRecyclerHolder>

    private val _goBack = MutableLiveData<Unit>()
    val goBack get() = _goBack as LiveData<Unit>

    fun onApplyClick(noteRecyclerHolder: NoteRecyclerHolder) {
        _result.postValue(noteRecyclerHolder)
        exit()
    }

    fun onCancelClick() {
        exit()
    }

    private fun exit() {
        _hideKeyboard.postValue(Unit)
        _goBack.postValue(Unit)
    }
 }