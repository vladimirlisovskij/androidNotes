package com.example.notes.presenter.noteEdit

import android.util.Log
import com.example.notes.base.BaseViewModel
import com.example.notes.domain.useCases.AddNoteUseCase
import com.example.notes.domain.useCases.DelNoteUseCase
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.presenter.entities.toDomain
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val delNoteUseCase: DelNoteUseCase,
    private val coordinator: Coordinator
): BaseViewModel()  {
    fun onApplyClick(noteRecyclerHolder: NoteRecyclerHolder) {
        disposable += addNoteUseCase(noteRecyclerHolder.toDomain())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    coordinator.openListNote()
                    Log.d("tag", "insert OK")
                },
                {
                    coordinator.openListNote()
                    Log.d("tag", "error $it.toString()")
                }
            )
    }

    fun onDelClick(id: Int) {
        disposable += delNoteUseCase(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    coordinator.openListNote()
                    Log.d("tag", "del OK")
                },
                {
                    coordinator.openListNote()
                    Log.d("tag", "error $it.toString()")
                }
            )
    }
}