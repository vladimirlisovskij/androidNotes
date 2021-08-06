package com.example.notes.domain.dataSource

import com.example.notes.domain.enitites.WidgetNoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface WidgetNoteDataSource {
    fun getWidgetNotesByID(widgetIDs: List<Long>): Single<HashMap<Long, WidgetNoteEntity>>

    fun insertWidgetNote(note: WidgetNoteEntity): Completable

    fun deleteWidgetNoteByID(widgetIDs: List<Long>): Completable
}