package com.example.notes.domain.useCases

import com.example.notes.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String) = repository.login(email, password)
}