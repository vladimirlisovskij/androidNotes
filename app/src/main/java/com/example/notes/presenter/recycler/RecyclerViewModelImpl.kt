package com.example.notes.presenter.recycler

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.notes.application.MainApplication
import com.example.notes.base.BaseViewModelImpl
import com.example.notes.domain.useCases.GetNotesUseCase
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.view.entities.NoteRecyclerHolder
import com.example.notes.view.entities.toPresentation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RecyclerViewModelImpl(
    private val getNotesUseCase: GetNotesUseCase,
    private val coordinator: Coordinator
): RecyclerViewModel, BaseViewModelImpl()  {
    override val noteList = MutableLiveData<List<NoteRecyclerHolder>>()

    init {
        disposable += getNotesUseCase()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { notes ->
                    Log.d("tag", "get OK")
                    noteList.postValue(notes.map {
                        it.toPresentation()
                    })
                },
                {
                    Log.d("tag", "error $it.toString()")
                }
            )
    }

    override fun onAddNoteClick() {
        coordinator.openNoteEditor(NoteRecyclerHolder(0, "", "", ""))
    }

    override fun onItemClick(noteRecyclerHolder: NoteRecyclerHolder) {
        coordinator.openNoteEditor(noteRecyclerHolder)
    }
}