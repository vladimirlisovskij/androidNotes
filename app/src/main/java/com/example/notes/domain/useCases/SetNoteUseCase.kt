package com.example.notes.domain.useCases

import com.example.notes.domain.enitites.NoteEntity
import com.example.notes.domain.repository.NoteRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class SetNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(noteEntity: NoteEntity): Completable {
        return repository.setNoteFB(noteEntity)
    }
}