package com.example.notes.presenter.backCoordinator

import io.reactivex.rxjava3.core.Observable
import java.util.*

class OnBackCollector(
    collector: Observable<Unit>
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