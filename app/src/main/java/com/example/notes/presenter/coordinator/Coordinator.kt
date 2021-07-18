package com.example.notes.presenter.coordinator

import android.graphics.Bitmap
import com.example.notes.presenter.entities.NoteRecyclerHolder
import com.example.notes.view.noteEdit.NoteEditView
import com.example.notes.view.recycler.ListNotesView
import com.example.notes.view.viewer.ViewerView
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

class Coordinator @Inject constructor(
    private val router: Router,
    private val collectorSubject: PublishSubject<Signal>
){
    companion object {
        const val EMPTY_CODE = "EMPTY_CODE"
    }

    private val stack = Stack<String>()

    fun addKey(key: String) {
        stack.push(key)
    }

    fun popKey() {
        if(!stack.empty()) stack.pop()
    }

    val keySubject: Observable<String> = collectorSubject.map { _ ->
        when(stack.isEmpty()) {
            true -> EMPTY_CODE
            false -> stack.peek()
        }
    }

    fun back() {
        router.exit()
    }

    fun openListNote() {
        router.newRootScreen(FragmentScreen{ ListNotesView.newInstance()})
    }

    fun openNoteEditor(noteRecyclerHolder: NoteRecyclerHolder) {
        router.navigateTo(FragmentScreen{ NoteEditView.newInstance(noteRecyclerHolder)})
    }

    fun openViewer(bitmap: Bitmap) {
        router.navigateTo(FragmentScreen{ ViewerView.newInstance(bitmap) })
    }
}