package com.example.notes.cleanArchitecture.presenter.recycler

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.classes.backCoordinator.OnBackCollector
import com.example.notes.classes.base.baseFragment.BaseViewModel
import com.example.notes.classes.coordinator.Coordinator
import com.example.notes.cleanArchitecture.domain.useCases.AddNoteUseCase
import com.example.notes.cleanArchitecture.domain.useCases.DelNoteUseCase
import com.example.notes.cleanArchitecture.domain.useCases.GetNotesUseCase
import com.example.notes.cleanArchitecture.domain.useCases.SetIsOnlineUseCase
import com.example.notes.cleanArchitecture.presenter.entities.PresenterNoteEntity
import com.example.notes.cleanArchitecture.presenter.entities.toDomain
import com.example.notes.cleanArchitecture.presenter.entities.toPresentation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RecyclerViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val delNoteUseCase: DelNoteUseCase,
    private val setIsOnlineUseCase: SetIsOnlineUseCase,
    private val coordinator: Coordinator,
    private val onBackCollector: OnBackCollector
): BaseViewModel()  {
    private var isSelected = false

    private val mutableNoteList = MutableLiveData<List<PresenterNoteEntity>>()
    val presenterNoteList: LiveData<List<PresenterNoteEntity>> = mutableNoteList

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
        getNotes()
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
                id="",
                header="",
                desc="",
                body="",
                image= listOf(),
                creationDate="",
                lastEditDate=""
            )
        ).simpleObservableSubscribe{
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
            .simpleObservableSubscribe{
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

    fun onLongTab() {
        isSelected = true
        mutableSelectedMode.postValue(true)
    }

    fun onNavigationBack() {
        isSelected = false
        mutableSelectedMode.postValue(false)
    }

    fun onDeleteClick(listPresenterNotes: List<PresenterNoteEntity>) {
        isSelected = false
        mutableSelectedMode.postValue(false)
        delNoteUseCase(listPresenterNotes.map { it.id }).andThen(getNotesUseCase()).simpleSingleSubscribe { notes ->
            Log.d("tag", "getNotes OK")
            mutableNoteList.postValue(notes.map {
                it.toPresentation()
            })
        }
    }

    fun onSignOut() {
        Firebase.auth.signOut()
        coordinator.openLogin()
    }

    fun getNotes() {
        getNotesUseCase().simpleSingleSubscribe { notes ->
            Log.d("tag", "getNotes OK")
            mutableNoteList.postValue(notes.map {
                it.toPresentation()
            })
        }
    }
}