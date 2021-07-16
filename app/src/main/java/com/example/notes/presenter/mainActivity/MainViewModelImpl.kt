package com.example.notes.presenter.mainActivity

import com.example.notes.base.BaseViewModelImpl
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.view.noteEdit.NoteEditView
import com.example.notes.view.recycler.ListNotesView
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class MainViewModelImpl: MainViewModel, BaseViewModelImpl() {

    @Inject
    lateinit var coordinator: Coordinator

    override fun onViewReady() {
        coordinator.openListNote()
    }
}