package com.example.notes.presenter.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageHolder(
    val images: List<ByteArray>
): Parcelable