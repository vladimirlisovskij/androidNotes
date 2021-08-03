package com.example.notes.classes.base.baseResultFragment

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.classes.base.baseFragment.BaseViewModel

abstract class  ResultViewModel: BaseViewModel() {
    protected val _setResult = MutableLiveData<Parcelable>()
    val setResult get() = _setResult as LiveData<Parcelable>
}