package com.example.notes.presenter.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.presenter.backCoordinator.OnBackCollector
import com.example.notes.presenter.base.baseResultFragment.ResultViewModel
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.entities.PresenterNoteEntity
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

    fun onEditorBack() {
        coordinator.back()
    }

    fun onGalleryBack() {
        _closeGallery.postValue(Unit)
    }

    fun sendResult(presenterNoteEntity: PresenterNoteEntity) {
        _setResult.postValue(presenterNoteEntity)
    }
}