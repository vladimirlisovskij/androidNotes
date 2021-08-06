package com.example.notes.domain.useCases

import com.example.notes.domain.enitites.WidgetNoteEntity
import com.example.notes.domain.repository.WidgetNoteRepository
import javax.inject.Inject

class InsertWidgetNoteUseCase @Inject constructor(
    private val repository: WidgetNoteRepository
) {
    operator fun invoke(note: WidgetNoteEntity) = repository.insertWidgetNote(note)
}

