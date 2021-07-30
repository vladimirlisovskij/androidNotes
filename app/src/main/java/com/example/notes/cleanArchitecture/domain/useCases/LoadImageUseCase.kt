package com.example.notes.cleanArchitecture.domain.useCases

import android.graphics.Bitmap
import com.example.notes.cleanArchitecture.domain.repository.Repository
import javax.inject.Inject

class LoadImageUseCase @Inject constructor(
    private val repository: Repository
) {
    fun multiLoadImage(key: List<String>) = repository.multiLoadImage(key)

    fun multiSaveImage(key: List<Bitmap>) = repository.multiSaveImage(key)
}