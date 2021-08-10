package com.example.notes.presenter.base.baseResultFragment

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.presenter.backCoordinator.OnBackCollector
import com.example.notes.presenter.base.baseFragment.BaseViewModel
import com.example.notes.presenter.coordinator.Coordinator

abstract class  ResultViewModel(
    private val onBackCollector: OnBackCollector,
    private val coordinator: Coordinator,
) : BaseViewModel(onBackCollector, coordinator) {
    protected val _setResult = MutableLiveData<Parcelable>()
    val setResult get() = _setResult as LiveData<Parcelable>
}