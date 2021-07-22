package com.example.notes.presenter.coordinator

import android.os.Parcelable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class FragmentResultEmitter(
    private val subject: PublishSubject<Parcelable>
) {
    fun sendResult(parcelable: Parcelable) {
        subject.onNext(parcelable)
    }
}

class FragmentResultCollector(
    val result: Observable<Parcelable>
)

class FragmentResultConstructor{
    private val subject= PublishSubject.create<Parcelable>()

    val fragmentResultEmitter = FragmentResultEmitter(subject)
    val fragmentResultCollector = FragmentResultCollector(subject)
}