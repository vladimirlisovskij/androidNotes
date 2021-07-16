package com.example.notes.domain.useCases

import com.example.notes.domain.repository.Repository
import io.reactivex.rxjava3.core.Completable

class DelNoteUseCase (
    private val repository: Repository
) {
    operator fun invoke(id: Int): Completable {
        return repository.deleteNote(id)
    }
}