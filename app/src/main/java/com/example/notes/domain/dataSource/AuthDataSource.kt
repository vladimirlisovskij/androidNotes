package com.example.notes.domain.dataSource

import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Completable

interface AuthDataSource {
    fun login(email: String, password: String): Completable

    fun register(email: String, password: String): Completable

    fun getUser(): FirebaseUser?
}