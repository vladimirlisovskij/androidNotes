package com.example.notes.base

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class ResultFragment<T: ResultViewModel>(
    @LayoutRes layoutID: Int
): BaseView<T>(layoutID) {
    var resultEmitter: PublishSubject<Parcelable>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.setResult.observe(viewLifecycleOwner) {
            resultEmitter?.onNext(it)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}

abstract class  ResultViewModel: BaseViewModel() {
    protected val mutableSetResult = MutableLiveData<Parcelable>()
    val setResult: LiveData<Parcelable> = mutableSetResult
}