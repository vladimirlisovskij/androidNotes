package com.example.notes.presenter.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.base.BaseViewModel
import com.example.notes.base.ResultViewModel
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.coordinator.OnBackCollector
import com.example.notes.presenter.entities.NoteRecyclerHolder
import javax.inject.Inject

class EditorViewModel @Inject constructor(
    private val coordinator: Coordinator,
    private val onBackCollector: OnBackCollector
)   : ResultViewModel() {
    private var isGalleryOpen = false

    private val _closeGallery = MutableLiveData<Unit>()
    val closeGallery get() = _closeGallery as LiveData<Unit>

    override fun onCreate() {
        super.onCreate()
        onBackCollector.subscribe {
            if (isGalleryOpen) {
                _closeGallery.postValue(Unit)
            } else {
                coordinator.back()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackCollector.disposeLastSubscription()
    }

    fun setIsGalleryState(value: Boolean) {
        isGalleryOpen = value
    }

    fun onEditorBack() {
        coordinator.back()
    }

    fun onGalleryBack() {
        _closeGallery.postValue(Unit)
    }

    fun sendResult(noteRecyclerHolder: NoteRecyclerHolder) {
        _setResult.postValue(noteRecyclerHolder)
    }
}