package com.example.notes.domain.useCases

import com.example.notes.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String) = repository.register(email, password)
}