package com.example.notes.cleanArchitecture.presenter.noteEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.classes.base.baseFragment.BaseViewModel
import com.example.notes.cleanArchitecture.presenter.entities.PresenterNoteEntity
import javax.inject.Inject

class NoteViewModel @Inject constructor(): BaseViewModel()  {
    private val _hideKeyboard = MutableLiveData<Unit>()
    val hideKeyboard get() = _hideKeyboard as LiveData<Unit>

    private val _result = MutableLiveData<PresenterNoteEntity>()
    val result get() = _result as LiveData<PresenterNoteEntity>

    private val _goBack = MutableLiveData<Unit>()
    val goBack get() = _goBack as LiveData<Unit>

    fun onApplyClick(presenterNoteEntity: PresenterNoteEntity) {
        _result.postValue(presenterNoteEntity)
        _hideKeyboard.postValue(Unit)
    }

    fun exit() {
        _hideKeyboard.postValue(Unit)
        _goBack.postValue(Unit)
    }
 }