package com.example.notes.presenter.coordinator

import io.reactivex.rxjava3.subjects.PublishSubject

enum class Signal { EMPTY }

class OnBackListener(
    private val emitter: PublishSubject<Signal>
){
    fun emit(){
        emitter.onNext(Signal.EMPTY)
    }
}