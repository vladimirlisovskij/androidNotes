package com.example.notes.presenter.coordinator

import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

class OnBackEmitter(
    private val emitter: PublishSubject<Unit>
){
    fun emit(){
        emitter.onNext(Unit)
    }
}

class OnBackCollector(
    private val collector: PublishSubject<Unit>
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

class OnBackFabric {
    private val subject = PublishSubject.create<Unit>()

    val emitter = OnBackEmitter(subject)
    val collector = OnBackCollector(subject)
}