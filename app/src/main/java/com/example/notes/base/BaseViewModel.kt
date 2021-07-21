package com.example.notes.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel: ViewModel() {
    protected operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
        this.add(disposable)
    }

    protected val mutableToastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = mutableToastMessage

    protected val disposable = CompositeDisposable()

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
        disposable.dispose()
    }
}