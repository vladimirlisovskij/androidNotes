package com.example.notes.presenter.backCoordinator

import io.reactivex.rxjava3.subjects.PublishSubject

class OnBackEmitter(
    private val emitter: PublishSubject<Unit>
){
    fun emit(){
        emitter.onNext(Unit)
    }
}