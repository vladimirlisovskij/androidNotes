package com.example.notes.domain.useCases

import com.example.notes.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getUser()
}