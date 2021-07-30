package com.example.notes.classes.backCoordinator

import io.reactivex.rxjava3.subjects.PublishSubject

class OnBackListener(subject: PublishSubject<Unit>) {
    companion object {
        fun create() = OnBackListener(PublishSubject.create())
    }

    val emitter = OnBackEmitter(subject)
    val collector = OnBackCollector(subject)
}