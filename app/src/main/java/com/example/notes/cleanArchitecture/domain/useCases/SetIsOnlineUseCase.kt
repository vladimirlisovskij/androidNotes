package com.example.notes.cleanArchitecture.domain.useCases

import com.example.notes.cleanArchitecture.domain.repository.NoteRepository
import javax.inject.Inject

class SetIsOnlineUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(isOnline: Boolean) = repository.setIsOnline(isOnline)
}