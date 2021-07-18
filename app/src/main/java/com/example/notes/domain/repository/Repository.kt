package com.example.notes.domain.repository

import android.graphics.Bitmap
import com.example.notes.domain.dataSource.DataSource
import com.example.notes.domain.enitites.NoteEntity
import com.example.notes.domain.enitites.replaceImage
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class Repository @Inject constructor(
    private val dataSource: DataSource
) {
    fun addNote(noteEntity: NoteEntity, bitmap: Bitmap?): Completable{
        return dataSource.deleteImageById(listOf(noteEntity.id)).andThen(Completable.fromSingle(
            dataSource.saveImage(bitmap).flatMap {
                dataSource.addNote(noteEntity.replaceImage(it))
            }
        ))
    }

    fun getNotes() = dataSource.getNotes()

    fun deleteNote(id: List<Long>): Completable = dataSource.deleteImageById(id).andThen(dataSource.deleteNote(id))

    fun loadImage(key: String) = dataSource.loadImage(key)
}