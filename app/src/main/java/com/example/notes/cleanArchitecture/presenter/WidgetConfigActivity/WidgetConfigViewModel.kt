package com.example.notes.cleanArchitecture.presenter.WidgetConfigActivity

import com.example.notes.classes.base.baseActivity.BaseActivityViewModel
import com.example.notes.classes.coordinator.Coordinator
import com.example.notes.cleanArchitecture.domain.useCases.InsertWidgetNoteUseCase
import com.example.notes.cleanArchitecture.presenter.entities.NoteRecyclerHolder
import com.example.notes.cleanArchitecture.presenter.entities.toWidgetDomain
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WidgetConfigViewModel @Inject constructor(
    private val coordinator: Coordinator,
    private val insertWidgetNoteUseCase: InsertWidgetNoteUseCase
): BaseActivityViewModel() {

    private val _resultEmitter = PublishSubject.create<NoteRecyclerHolder>()
    val resultEmitter get() = _resultEmitter as Observable<NoteRecyclerHolder>

    fun onReady(id: Int) {
        coordinator.startNoteEditWithRoot(
            NoteRecyclerHolder(
                id=0,
                header="",
                desc="",
                body="",
                image= listOf(),
                creationDate="",
                lastEditDate=""
            )
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                (it as? NoteRecyclerHolder)?.let { note ->
                    _resultEmitter.onNext(note)
                    note.id = id.toLong()
                    insertWidgetNoteUseCase(note.toWidgetDomain())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe()
                }
            }
    }
}