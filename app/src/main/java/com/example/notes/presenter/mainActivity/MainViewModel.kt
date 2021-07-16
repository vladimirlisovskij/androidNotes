package com.example.notes.presenter.mainActivity

import com.example.notes.base.BaseViewModel
import com.example.notes.presenter.coordinator.Coordinator
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val coordinator: Coordinator
): BaseViewModel() {
    fun onViewReady() {
        coordinator.openListNote()
    }
}