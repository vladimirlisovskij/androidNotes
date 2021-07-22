package com.example.notes.presenter.noteEdit

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.base.BaseViewModel
import com.example.notes.domain.useCases.AddNoteUseCase
import com.example.notes.domain.useCases.DelNoteUseCase
import com.example.notes.domain.useCases.LoadImageUseCase
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.coordinator.OnBackCollector
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.entities.toDomain
import com.example.notes.presenter.gallery.GalleryViewModel
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val delNoteUseCase: DelNoteUseCase,
    private val coordinator: Coordinator,
    private val onBackCollector: OnBackCollector
): BaseViewModel()  {
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
        addNoteUseCase(noteRecyclerHolder.toDomain()).toSingle{}.simpleSingleSubscribe {
            exit()
        }
    }

    fun onDelClick(id: Long) {
        delNoteUseCase(listOf(id)).toSingle{ }.simpleSingleSubscribe{
            exit()
        }
    }

    fun onOpenImage(keys: List<String>){
        coordinator.startGallery(keys)
            .subscribe {
                (it as? GalleryViewModel.GalleryResult)?.let { res ->
                    mutableListRefs.postValue(res.links)
                }
            }
            .addToComposite()
    }

    fun onCancelClick() {
        exit()
    }

    private fun exit() {
        mutableHideKeyboard.postValue(Unit)
        coordinator.back()
    }
 }