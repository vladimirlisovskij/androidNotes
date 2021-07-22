package com.example.notes.domain.useCases

import android.graphics.Bitmap
import com.example.notes.domain.enitites.NoteEntity
import com.example.notes.domain.repository.Repository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(noteEntity: NoteEntity): Completable {
        return repository.addNote(noteEntity)
    }
}