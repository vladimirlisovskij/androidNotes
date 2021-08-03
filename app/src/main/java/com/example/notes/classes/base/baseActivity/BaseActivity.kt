package com.example.notes.classes.base.baseActivity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T: BaseActivityViewModel> (
    @LayoutRes protected val layoutRes: Int
): AppCompatActivity(layoutRes) {
    abstract val viewModel: T
}

