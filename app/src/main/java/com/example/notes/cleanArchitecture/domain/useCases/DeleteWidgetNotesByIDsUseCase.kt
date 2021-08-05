package com.example.notes.cleanArchitecture.domain.useCases

import com.example.notes.cleanArchitecture.domain.repository.WidgetNoteRepository
import javax.inject.Inject

class DeleteWidgetNotesByIDsUseCase @Inject constructor(
    private val repository: WidgetNoteRepository
) {
    operator fun invoke(widgetIDs: List<Long>) = repository.deleteWidgetNoteByIDs(widgetIDs)
}