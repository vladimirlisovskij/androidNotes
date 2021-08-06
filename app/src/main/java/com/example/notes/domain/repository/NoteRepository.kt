package com.example.notes.domain.repository

import com.example.notes.domain.dataSource.NoteDataSource
import com.example.notes.domain.enitites.NoteEntity
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val dataSource: NoteDataSource
) {
    fun addNoteFB(noteEntity: NoteEntity): Completable = dataSource.addNoteFB(noteEntity)

    fun getNotesFB() = dataSource.getNotesFB()

    fun deleteNoteFB(id: List<String>): Completable = dataSource.deleteNoteFB(id)

    fun setIsOnline(isOnline: Boolean) = dataSource.setIsOnline(isOnline)
}

