package com.example.notes.domain.useCases

import com.example.notes.domain.repository.Repository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class DelNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: List<Long>): Completable {
        return repository.deleteNote(id)
    }
}