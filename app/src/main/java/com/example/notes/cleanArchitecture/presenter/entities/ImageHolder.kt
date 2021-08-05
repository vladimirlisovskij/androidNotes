package com.example.notes.cleanArchitecture.presenter.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageHolder(
    val images: List<ByteArray>
): Parcelable