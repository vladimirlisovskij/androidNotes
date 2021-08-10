package com.example.notes.data.dataSource

import com.example.notes.data.dataBase.entitie.toDomain
import com.example.notes.data.dataBase.entitie.toEmployee
import com.example.notes.data.exceptions.UserAuthException
import com.example.notes.domain.dataSource.AuthDataSource
import com.example.notes.domain.dataSource.NoteDataSource
import com.example.notes.domain.enitites.NoteEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NoteDataSourceImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : NoteDataSource {
    private val database = Firebase.firestore

    override fun addNoteFB(noteEntity: NoteEntity): Completable {
        return Completable.create { emitter ->
            authDataSource.getUser()?.let { user ->
                with(database.collection(user.uid)) {
                    add(noteEntity)
                        .addOnSuccessListener {
                            emitter.onComplete()
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
                }
            } ?: emitter.onError(UserAuthException())
        }
    }

    override fun setNoteFB(noteEntity: NoteEntity): Completable {
        return Completable.create { emitter ->
            authDataSource.getUser()?.let { user ->
                with(database.collection(user.uid)) {
                    document(noteEntity.id).set(noteEntity)
                        .addOnSuccessListener {
                            emitter.onComplete()
                        }
                        .addOnFailureListener {
                            emitter.onError(it)
                        }
                }
            } ?: emitter.onError(UserAuthException())
        }
    }

    override fun setIsOnline(isOnline: Boolean): Completable {
        return Completable.create { emitter ->
            if (isOnline) {
                database.enableNetwork()
            } else {
                database.disableNetwork()
            }.addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }

    override fun deleteNoteFB(noteIDs: List<String>): Completable {
        return authDataSource.getUser()?.let { user ->
            Completable.fromAction {
                with(database.collection(user.uid)) {
                    noteIDs.forEach { id ->
                        document(id).delete()
                    }
                }
            }
        } ?: Completable.error(UserAuthException())
    }

    override fun getNotesFB(): Single<List<NoteEntity>> {
        return authDataSource.getUser()?.let { user ->
            Single.create { emitter ->
                database.collection(user.uid)
                    .get()
                    .addOnSuccessListener {
                        emitter.onSuccess(it.map { doc ->
                            doc.toEmployee().toDomain()
                        })
                    }
                    .addOnFailureListener {
                        emitter.onError(it)
                    }
            }
        } ?: Single.error(UserAuthException())
    }
}