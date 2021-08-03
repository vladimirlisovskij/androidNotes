package com.example.notes.cleanArchitecture.domain.useCases

import com.example.notes.cleanArchitecture.domain.repository.Repository
import javax.inject.Inject

class GetNotesByWidgetIdUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id: List<Long>) = repository.getWidgetNotesByIDs(id)
}