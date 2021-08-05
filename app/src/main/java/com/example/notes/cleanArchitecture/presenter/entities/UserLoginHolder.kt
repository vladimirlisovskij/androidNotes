package com.example.notes.cleanArchitecture.presenter.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserLoginHolder(
    val email: String,
    val password: String
): Parcelable