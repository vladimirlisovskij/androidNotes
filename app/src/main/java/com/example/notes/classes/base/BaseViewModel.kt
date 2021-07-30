package com.example.notes.classes.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseViewModel: ViewModel() {
    private val mutableToastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = mutableToastMessage

    protected fun makeToast(message: String) {
        mutableToastMessage.postValue(message)
    }

    protected fun Disposable.addToComposite() {
        compositeDisposable.add(this)
    }

    protected fun <T> Observable<T>.simpleObservableSubscribe(onSuccess: Consumer<T>) {
        this.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError {
                Log.d("tag", it.toString())
            }
            .subscribe(onSuccess)
            .addToComposite()
    }

    protected fun <T> Single<T>.simpleSingleSubscribe(onSuccess: Consumer<T>) {
        this.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError {
                Log.d("tag", it.toString())
            }
            .subscribe(onSuccess)
            .addToComposite()
    }

    protected fun <T> Single<T>.simpleSingleSubscribe(onSuccess: Consumer<T>, onError: Consumer<Throwable>) {
        this.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError {
                Log.d("tag", it.toString())
            }
            .subscribe(onSuccess, onError)
            .addToComposite()
    }

    private val compositeDisposable = CompositeDisposable()

    open fun onCreate() { }

    open fun onCreateView() { }

    open fun onViewCreated() { }

    open fun onViewStateRestored() { }

    open fun onStart() { }

    open fun onResume() { }

    open fun onPause() { }

    open fun onStop() { }

    open fun onSaveInstanceState() { }

    open fun onDestroyView() { }

    open fun onDestroy() {
        compositeDisposable.dispose()
    }
}