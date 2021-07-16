package com.example.notes.presenter.noteEdit

import android.util.Log
import com.example.notes.base.BaseViewModelImpl
import com.example.notes.domain.useCases.AddNoteUseCase
import com.example.notes.domain.useCases.DelNoteUseCase
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.view.entities.NoteRecyclerHolder
import com.example.notes.view.entities.toDomain
import com.example.notes.view.entities.toPresentation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class NoteViewModelImpl(
    private val addNoteUseCase: AddNoteUseCase,
    private val delNoteUseCase: DelNoteUseCase,
    private val coordinator: Coordinator
): NoteViewModel, BaseViewModelImpl()  {
    override fun onApplyClick(noteRecyclerHolder: NoteRecyclerHolder) {
        addNoteUseCase(noteRecyclerHolder.toDomain())
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

    override fun onDelClick(id: Int) {
        delNoteUseCase(id)
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