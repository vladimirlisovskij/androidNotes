package com.example.notes.domain.useCases

import com.example.notes.domain.enitites.NoteEntity
import com.example.notes.domain.repository.NoteRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(): Single<List<NoteEntity>> {
        return repository.getNotesFB()
    }
}