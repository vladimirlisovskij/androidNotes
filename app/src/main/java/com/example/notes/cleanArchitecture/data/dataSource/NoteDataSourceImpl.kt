package com.example.notes.cleanArchitecture.data.dataSource

import com.example.notes.classes.exceptions.UserAuthException
import com.example.notes.cleanArchitecture.data.dataBase.entitie.toDomain
import com.example.notes.cleanArchitecture.data.dataBase.entitie.toEmployee
import com.example.notes.cleanArchitecture.domain.dataSource.NoteDataSource
import com.example.notes.cleanArchitecture.domain.enitites.NoteEntity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NoteDataSourceImpl @Inject constructor(

): NoteDataSource {
    private val database = Firebase.firestore
    private val auth = Firebase.auth


    override fun addNoteFB(noteEntity: NoteEntity): Completable {
        return Completable.create { emitter ->
            auth.currentUser?.let { user ->
                with(database.collection(user.uid)){
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

    override fun setIsOnline(isOnline: Boolean): Completable {
        return Completable.create {emitter ->
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
        return auth.currentUser?.let { user ->
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
        return auth.currentUser?.let { user ->
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