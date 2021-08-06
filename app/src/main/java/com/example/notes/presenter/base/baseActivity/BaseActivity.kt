package com.example.notes.presenter.base.baseActivity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.di.Injector.initInjector

abstract class BaseActivity<T: BaseActivityViewModel> (
    @LayoutRes protected val layoutRes: Int
): AppCompatActivity(layoutRes) {
    abstract val viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
    }
}

