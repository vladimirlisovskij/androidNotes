package com.example.notes.cleanArchitecture.domain.useCases

import android.graphics.Bitmap
import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import com.example.notes.cleanArchitecture.domain.repository.Repository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(noteEntity: NoteEntity): Completable {
        return repository.addNote(noteEntity)
    }
}