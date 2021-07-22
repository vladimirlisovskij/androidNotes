package com.example.notes.domain.useCases

import android.graphics.Bitmap
import com.example.notes.domain.repository.Repository
import javax.inject.Inject

class LoadImageUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(key: String) = repository.loadImage(key)

    fun multiLoadImage(key: List<String>) = repository.multiLoadImage(key)

    fun multiSaveImage(key: List<Bitmap>) = repository.multiSaveImage(key)
}