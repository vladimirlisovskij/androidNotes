package com.example.notes.cleanArchitecture.domain.useCases

import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import com.example.notes.cleanArchitecture.domain.repository.Repository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Single<List<NoteEntity>> {
        return repository.getNotes()
    }
}