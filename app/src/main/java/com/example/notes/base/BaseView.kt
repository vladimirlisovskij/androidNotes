package com.example.notes.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

interface BaseViewModel {
    operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
        this.add(disposable)
    }

    val disposable: CompositeDisposable

    fun onViewDestroy() {
        disposable.dispose()
    }
}

abstract class BaseViewModelImpl: BaseViewModel {
    override val disposable = CompositeDisposable()
}

abstract class BaseView<T: BaseViewModel>(
    @LayoutRes layoutID: Int
): Fragment(layoutID) {
    abstract val viewModel: T

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onViewDestroy()
    }
}
