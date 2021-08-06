package com.example.notes.presenter.mainActivity

import com.example.notes.presenter.base.baseActivity.BaseActivityViewModel
import com.example.notes.presenter.coordinator.Coordinator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(
    private val coordinator: Coordinator
): BaseActivityViewModel() {
    init {
        coordinator.openLogin()
    }
}