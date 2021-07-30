package com.example.notes.cleanArchitecture.presenter.mainActivity

import com.example.notes.classes.base.BaseViewModel
import com.example.notes.classes.coordinator.Coordinator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewModel @Inject constructor(
    private val coordinator: Coordinator
): BaseViewModel() {
    init {
        coordinator.openListNote()
    }
}