package com.example.notes.domain.useCases

import com.example.notes.domain.repository.Repository
import javax.inject.Inject

class LoadImageUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(key: String) = repository.loadImage(key)
}