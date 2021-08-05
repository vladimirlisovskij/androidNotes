package com.example.notes.cleanArchitecture.domain.useCases

import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import com.example.notes.cleanArchitecture.domain.repository.NoteRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(noteEntity: NoteEntity): Completable {
        return repository.addNoteFB(noteEntity)
    }
}