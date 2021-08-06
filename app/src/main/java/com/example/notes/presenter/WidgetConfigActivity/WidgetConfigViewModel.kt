package com.example.notes.presenter.WidgetConfigActivity

import com.example.notes.presenter.base.baseActivity.BaseActivityViewModel
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.domain.useCases.InsertWidgetNoteUseCase
import com.example.notes.presenter.entities.PresenterNoteEntity
import com.example.notes.presenter.entities.PresenterWidgetNoteEntity
import com.example.notes.presenter.entities.toWidgetDomain
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

    private val _resultEmitter = PublishSubject.create<PresenterWidgetNoteEntity>()
    val resultEmitter get() = _resultEmitter as Observable<PresenterWidgetNoteEntity>

    fun onReady(id: Int) {
        coordinator.startNoteEditWithRoot(
            PresenterNoteEntity(
                id="",
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
                (it as? PresenterWidgetNoteEntity)?.let { note ->
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