package com.example.notes.presenter.coordinator

import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

enum class Signal { EMPTY}

class OnBackListener(
    private val emitter: PublishSubject<String>,
    private val activityCollector: PublishSubject<Signal>,
    private val codeEmitter: PublishSubject<String>
){
    companion object {
        const val DEFAULT_CODE = "DEFAULT"
    }

    interface BackListener {
        fun onBack()
    }

    private var disposable: Disposable? = null
    private var codeDisposable: Disposable? = null

    private var code = DEFAULT_CODE

    fun registerForCalls(appCompatActivity: BackListener){
        dispose()
        disposable = activityCollector.subscribe {
            appCompatActivity.onBack()
        }
        codeDisposable = codeEmitter.subscribe {
            code = it
        }
    }

    fun emit(){
        emitter.onNext(code)
    }

    fun dispose() {
        codeDisposable?.dispose()
        disposable?.dispose()
    }
}

class OnBackCollector(
    private val activityCollector: PublishSubject<Signal>,
    private val collectorSubject: PublishSubject<String>,
    private val codeEmitter: PublishSubject<String>
) {

    var code: String = OnBackListener.DEFAULT_CODE
        set (value) {
            field = value
            codeEmitter.onNext(field)
        }

    fun getSubject(): PublishSubject<String>{
        val oldCode = code
        return PublishSubject.create<String>().apply {
            collectorSubject.subscribe(this)
            doOnDispose {
                this@OnBackCollector.code = oldCode
            }
        }
    }

    fun emit(){
        activityCollector.onNext(Signal.EMPTY)
    }
}