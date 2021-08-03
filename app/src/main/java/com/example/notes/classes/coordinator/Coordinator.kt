package com.example.notes.classes.coordinator

import android.os.Parcelable
import android.util.Log
import com.example.notes.classes.base.baseResultFragment.ResultFragment
import com.example.notes.classes.base.baseResultFragment.ResultViewModel
import com.example.notes.cleanArchitecture.presenter.entities.NoteRecyclerHolder
import com.example.notes.cleanArchitecture.view.editor.EditorView
import com.example.notes.cleanArchitecture.view.recycler.ListNotesView
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Coordinator @Inject constructor(
    private val router: Router
){
    fun back() {
        router.exit()
    }

    fun openListNote() {
        router.newRootScreen(FragmentScreen{ ListNotesView.newInstance() })
    }

    private fun <T: ResultViewModel> startForResult(fragment: ResultFragment<T>): Observable<Parcelable> {
        val publishSubject = PublishSubject.create<Parcelable>()
        fragment.resultEmitter = publishSubject
        router.navigateTo(FragmentScreen{ fragment })
        return publishSubject
    }

    private fun <T: ResultViewModel> startForResultWithRoot(fragment: ResultFragment<T>): Observable<Parcelable> {
        Log.d("tag", "start")
        val publishSubject = PublishSubject.create<Parcelable>()
        fragment.resultEmitter = publishSubject
        router.newRootScreen(FragmentScreen{ fragment })
        return publishSubject
    }

    fun startNoteEdit(noteRecyclerHolder: NoteRecyclerHolder) = startForResult(EditorView.newInstance(noteRecyclerHolder))

    fun startNoteEditWithRoot(noteRecyclerHolder: NoteRecyclerHolder) = startForResultWithRoot(EditorView.newInstance(noteRecyclerHolder))
}

