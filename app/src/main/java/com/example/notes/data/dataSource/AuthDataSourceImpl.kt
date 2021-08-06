package com.example.notes.data.dataSource

import com.example.notes.domain.dataSource.AuthDataSource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(

): AuthDataSource {
    private val auth = Firebase.auth

    override fun login(email: String, password: String): Completable {
        return Completable.create { emitter ->
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }

    override fun register(email: String, password: String): Completable {
        return Completable.create { emitter ->
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }

    override fun getUser() = auth.currentUser
}