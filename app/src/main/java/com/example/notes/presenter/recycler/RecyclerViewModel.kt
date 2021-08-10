package com.example.notes.presenter.recycler

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.domain.useCases.*
import com.example.notes.presenter.backCoordinator.OnBackCollector
import com.example.notes.presenter.base.baseFragment.BaseViewModel
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.entities.NoteRecyclerItems
import com.example.notes.presenter.entities.PresenterNoteEntity
import com.example.notes.presenter.entities.toDomain
import com.example.notes.presenter.entities.toPresentation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RecyclerViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val setNoteUseCase: SetNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val delNoteUseCase: DelNoteUseCase,
    private val setIsOnlineUseCase: SetIsOnlineUseCase,
    private val coordinator: Coordinator,
    private val onBackCollector: OnBackCollector
) : BaseViewModel(onBackCollector, coordinator) {
    private var isSelected = false

    private val mutableNoteList = MutableLiveData<List<NoteRecyclerItems>>()
    val presenterNoteList: LiveData<List<NoteRecyclerItems>> = mutableNoteList

    private val mutableSelectedMode = MutableLiveData<Boolean>()
    val selectedMode: LiveData<Boolean> = mutableSelectedMode

    private val _enableRefreshing = MutableLiveData<Boolean>()
    val enableRefreshing: LiveData<Boolean> get() = _enableRefreshing

    override fun onResume() {
        super.onResume()
        onBackCollector.subscribe {
            if (isSelected) onNavigationBack()
            else coordinator.back()
        }
        getNotes()
    }

    override fun onBackClick() {
        if (isSelected) onNavigationBack()
        else super.onBackClick()
    }

    fun setIsOnline(isOnline: Boolean) {
        setIsOnlineUseCase(isOnline)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun onAddNoteClick() {
        coordinator.startNoteEdit(
            PresenterNoteEntity(
                id = "",
                header = "",
                desc = "",
                body = "",
                image = listOf(),
                creationDate = "",
                lastEditDate = ""
            )
        ).simpleObservableSubscribe {
            (it as? PresenterNoteEntity)?.let { note ->
                coordinator.back()
                addNoteUseCase(note.toDomain())
                    .toSingle { }
                    .simpleSingleSubscribe {
                        getNotes()
                    }
            }
        }
    }

    fun onItemClick(presenterNoteEntity: PresenterNoteEntity) {
        coordinator.startNoteEdit(presenterNoteEntity)
            .simpleObservableSubscribe {
                (it as? PresenterNoteEntity)?.let { note ->
                    coordinator.back()
                    setNoteUseCase(note.toDomain())
                        .toSingle { }
                        .simpleSingleSubscribe {
                            getNotes()
                        }
                }
            }
    }

    fun onLongTab() {
        isSelected = true
        mutableSelectedMode.postValue(true)
    }

    fun onNavigationBack() {
        isSelected = false
        mutableSelectedMode.postValue(false)
    }

    fun onDeleteClick(listPresenterNotes: List<PresenterNoteEntity>) {
        mutableNoteList.postValue(listOf(NoteRecyclerItems.ProgressItem()))
        isSelected = false
        mutableSelectedMode.postValue(false)
        delNoteUseCase(listPresenterNotes.map { it.id }).andThen(getNotesUseCase())
            .simpleSingleSubscribe { notes ->
                Log.d("tag", "getNotes OK")
                mutableNoteList.postValue(notes.map {
                    NoteRecyclerItems.NoteItem(it.toPresentation())
                })
            }
    }

    fun onSignOut() {
        Firebase.auth.signOut()
        coordinator.openLogin()
    }

    fun getNotes() {
        _enableRefreshing.postValue(true)
        mutableNoteList.postValue(listOf(NoteRecyclerItems.ProgressItem()))
        getNotesUseCase().simpleSingleSubscribe { notes ->
            Log.d("tag", "getNotes OK")
            _enableRefreshing.postValue(false)
            mutableNoteList.postValue(notes.map {
                NoteRecyclerItems.NoteItem(it.toPresentation())
            })
        }
    }
}