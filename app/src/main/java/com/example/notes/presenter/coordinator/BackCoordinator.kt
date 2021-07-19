package com.example.notes.presenter.coordinator

import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

enum class Signal { EMPTY }

class OnBackEmitter(
    private val emitter: PublishSubject<Signal>
){
    fun emit(){
        emitter.onNext(Signal.EMPTY)
    }
}

class OnBackCollector(
    private val collector: PublishSubject<Signal>
) {
    private val commandStack = Stack<(() -> Unit)>()

    init {
        collector.subscribe {
            if (!commandStack.empty()) commandStack.peek().invoke()
        }
    }

    fun subscribe(function: (()-> Unit)) {
        commandStack.push(function)
    }

    fun disposeLastSubscription() {
        if (!commandStack.empty()) commandStack.pop()
    }
}

class OnBackFabric() {
    private val subject = PublishSubject.create<Signal>()

    val emitter = OnBackEmitter(subject)
    val collector = OnBackCollector(subject)
}