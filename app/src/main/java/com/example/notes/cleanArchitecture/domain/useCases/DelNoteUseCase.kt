package com.example.notes.cleanArchitecture.domain.useCases

import com.example.notes.cleanArchitecture.domain.repository.Repository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class DelNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: List<Long>): Completable {
        return repository.deleteNote(id)
    }
}