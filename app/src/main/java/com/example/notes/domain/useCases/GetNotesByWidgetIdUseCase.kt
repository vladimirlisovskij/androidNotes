package com.example.notes.domain.useCases

import com.example.notes.domain.repository.WidgetNoteRepository
import javax.inject.Inject

class GetNotesByWidgetIdUseCase @Inject constructor(
    private val repository: WidgetNoteRepository
) {
    operator fun invoke(id: List<Long>) = repository.getWidgetNotesByIDs(id)
}