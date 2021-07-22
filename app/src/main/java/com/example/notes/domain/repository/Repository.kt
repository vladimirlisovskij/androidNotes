package com.example.notes.domain.repository

import android.graphics.Bitmap
import com.example.notes.domain.dataSource.DataSource
import com.example.notes.domain.enitites.NoteEntity
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataSource: DataSource
) {
    fun addNote(noteEntity: NoteEntity): Completable{
        return Completable.fromSingle(dataSource.addNote(noteEntity))
    }

    fun getNotes() = dataSource.getNotes()

    fun deleteNote(id: List<Long>): Completable = dataSource.deleteImageById(id).andThen(dataSource.deleteNote(id))

    fun loadImage(key: String) = dataSource.loadImage(key)

    fun multiLoadImage(key: List<String>) = dataSource.multiLoadImage(key)

    fun multiSaveImage(key: List<Bitmap>) = dataSource.multiSaveImage(key)
}