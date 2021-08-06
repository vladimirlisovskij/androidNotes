package com.example.notes.domain.useCases

import com.example.notes.domain.repository.NoteRepository
import javax.inject.Inject

class SetIsOnlineUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(isOnline: Boolean) = repository.setIsOnline(isOnline)
}