package com.example.notes.domain.repository

import com.example.notes.domain.dataSource.DataSource
import com.example.notes.domain.enitites.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class Repository {

    @Inject lateinit var dataSource: DataSource

    fun addNote(noteEntity: NoteEntity): Completable {
        return dataSource.addNote(noteEntity)
    }

    fun getNotes(): Single<List<NoteEntity>> {
        return dataSource.getNotes()
    }

    fun deleteNote(id: Int): Completable {
        return dataSource.delete(id)
    }
}