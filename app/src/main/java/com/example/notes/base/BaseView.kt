package com.example.notes.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseView<T: BaseViewModel>(
    @LayoutRes layoutID: Int
): Fragment(layoutID) {
    abstract val viewModel: T
}
