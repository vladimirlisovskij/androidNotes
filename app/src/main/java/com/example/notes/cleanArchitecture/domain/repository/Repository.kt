package com.example.notes.cleanArchitecture.domain.repository

import android.graphics.Bitmap
import com.example.notes.cleanArchitecture.domain.dataSource.DataSource
import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataSource: DataSource
) {
    fun addNote(noteEntity: NoteEntity): Completable = Completable.fromSingle(dataSource.addNote(noteEntity))

    fun getNotes() = dataSource.getNotes()

    fun deleteNote(id: List<Long>): Completable = dataSource.deleteImageById(id).andThen(dataSource.deleteNote(id))

    fun multiLoadImage(key: List<String>) = dataSource.multiLoadImage(key)

    fun multiSaveImage(key: List<Bitmap>) = dataSource.multiSaveImage(key)
}