package com.example.notes.cleanArchitecture.domain.useCases

import com.example.notes.cleanArchitecture.domain.enitites.WidgetNoteEntity
import com.example.notes.cleanArchitecture.domain.repository.WidgetNoteRepository
import javax.inject.Inject

class InsertWidgetNoteUseCase @Inject constructor(
    private val repository: WidgetNoteRepository
) {
    operator fun invoke(note: WidgetNoteEntity) = repository.insertWidgetNote(note)
}

