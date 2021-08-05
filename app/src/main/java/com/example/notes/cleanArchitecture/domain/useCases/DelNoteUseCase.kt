package com.example.notes.cleanArchitecture.domain.useCases

import com.example.notes.cleanArchitecture.domain.repository.NoteRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class DelNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(id: List<String>): Completable {
        return repository.deleteNoteFB(id)
    }
}