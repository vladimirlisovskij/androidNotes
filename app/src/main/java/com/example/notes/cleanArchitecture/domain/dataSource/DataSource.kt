package com.example.notes.cleanArchitecture.domain.dataSource

import android.graphics.Bitmap
import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import com.example.notes.cleanArchitecture.domain.enitites.WidgetNoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface DataSource {
    fun addNote(noteEntity: NoteEntity): Single<Long>

    fun deleteNote(noteIDs: List<Long>): Completable

    fun getNotes(): Single<List<NoteEntity>>

    fun multiLoadImage(keys: List<String>): Single<List<Bitmap>>

    fun multiSaveImage(keys: List<Bitmap>): Single<List<String>>

    fun deleteImageByID(noteIDs: List<Long>): Completable

    fun getWidgetNotesByID(widgetIDs: List<Long>): Single<HashMap<Long, WidgetNoteEntity>>

    fun insertWidgetNote(note: WidgetNoteEntity): Completable

    fun deleteWidgetNoteByID(widgetIDs: List<Long>): Completable
}