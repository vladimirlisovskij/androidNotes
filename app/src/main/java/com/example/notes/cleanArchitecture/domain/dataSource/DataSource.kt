package com.example.notes.cleanArchitecture.domain.dataSource

import android.graphics.Bitmap
import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface DataSource {
    fun addNote(noteEntity: NoteEntity): Single<Long>

    fun deleteNote(noteID: List<Long>): Completable

    fun getNotes(): Single<List<NoteEntity>>

    fun multiLoadImage(key: List<String>): Single<List<Bitmap>>

    fun multiSaveImage(keys: List<Bitmap>): Single<List<String>>

    fun deleteImageById(noteID: List<Long>): Completable
}