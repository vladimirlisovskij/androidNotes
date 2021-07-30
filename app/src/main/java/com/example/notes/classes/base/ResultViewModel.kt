package com.example.notes.classes.base

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class  ResultViewModel: BaseViewModel() {
    protected val _setResult = MutableLiveData<Parcelable>()
    val setResult get() = _setResult as LiveData<Parcelable>
}