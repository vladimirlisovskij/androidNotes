package com.example.notes.domain.dataSource

import com.example.notes.domain.enitites.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface DataSource {
    fun addNote(noteEntity: NoteEntity): Completable

    fun delete(noteID: Int): Completable

    fun getNotes(): Single<List<NoteEntity>>
}