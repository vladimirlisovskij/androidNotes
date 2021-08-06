package com.example.notes.domain.useCases

import com.example.notes.domain.repository.WidgetNoteRepository
import javax.inject.Inject

class DeleteWidgetNotesByIDsUseCase @Inject constructor(
    private val repository: WidgetNoteRepository
) {
    operator fun invoke(widgetIDs: List<Long>) = repository.deleteWidgetNoteByIDs(widgetIDs)
}