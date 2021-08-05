package com.example.notes.cleanArchitecture.domain.dataSource

import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface NoteDataSource {
    fun addNoteFB(noteEntity: NoteEntity): Completable

    fun deleteNoteFB(noteIDs: List<String>): Completable

    fun getNotesFB(): Single<List<NoteEntity>>

    fun setIsOnline(isOnline: Boolean): Completable
}