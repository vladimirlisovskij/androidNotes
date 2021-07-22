package com.example.notes.presenter.noteEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.base.ResultViewModel
import com.example.notes.domain.useCases.AddNoteUseCase
import com.example.notes.domain.useCases.DelNoteUseCase
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.coordinator.OnBackCollector
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.entities.toDomain
import com.example.notes.presenter.gallery.GalleryViewModel
import com.example.notes.view.gallery.GalleryView
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val delNoteUseCase: DelNoteUseCase,
    private val coordinator: Coordinator,
    private val onBackCollector: OnBackCollector
): ResultViewModel()  {
    private val mutableHideKeyboard = MutableLiveData<Unit>()
    val hideKeyboard: LiveData<Unit> = mutableHideKeyboard

    private val mutableListRefs = MutableLiveData<List<String>>()
    val listRefs: LiveData<List<String>> = mutableListRefs

    override fun onCreate() {
        super.onCreate()
        onBackCollector.subscribe {
            exit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackCollector.disposeLastSubscription()
    }

    fun onApplyClick(noteRecyclerHolder: NoteRecyclerHolder) {
        mutableSetResult.postValue(noteRecyclerHolder)
        exit()
    }

    fun onOpenImage(keys: List<String>){
        coordinator.startForResult(GalleryView.newInstance(keys)).simpleObservableSubscribe {
            (it as? GalleryViewModel.GalleryResult)?.let { res ->
                mutableListRefs.postValue(res.links)
            }
        }
    }

    fun onCancelClick() {
        exit()
    }

    private fun exit() {
        mutableHideKeyboard.postValue(Unit)
        coordinator.back()
    }
 }