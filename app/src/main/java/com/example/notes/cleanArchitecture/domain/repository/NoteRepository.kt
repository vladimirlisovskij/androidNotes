package com.example.notes.cleanArchitecture.domain.repository

import com.example.notes.cleanArchitecture.domain.dataSource.NoteDataSource
import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
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