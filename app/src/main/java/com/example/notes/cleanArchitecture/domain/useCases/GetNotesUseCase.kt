package com.example.notes.cleanArchitecture.domain.useCases

import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import com.example.notes.cleanArchitecture.domain.repository.NoteRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Single<List<NoteEntity>> {
        return repository.getNotesFB()
    }
}