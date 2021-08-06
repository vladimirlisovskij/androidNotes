package com.example.notes.presenter.coordinator

import android.os.Parcelable
import com.example.notes.presenter.base.baseResultFragment.ResultFragment
import com.example.notes.presenter.base.baseResultFragment.ResultViewModel
import com.example.notes.presenter.entities.PresenterNoteEntity
import com.example.notes.view.editor.EditorView
import com.example.notes.view.login.LoginView
import com.example.notes.view.recycler.ListNotesView
import com.example.notes.view.signin.SignInView
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

    fun openLogin() {
        router.newRootScreen(FragmentScreen{ LoginView.newInstance() })
    }

    fun openSignIn() {
        router.navigateTo(FragmentScreen{ SignInView.newInstance() })
    }

    private fun <T: ResultViewModel> startForResult(fragment: ResultFragment<T>): Observable<Parcelable> {
        val publishSubject = PublishSubject.create<Parcelable>()
        fragment.resultEmitter = publishSubject
        router.navigateTo(FragmentScreen{ fragment })
        return publishSubject
    }

    private fun <T: ResultViewModel> startForResultWithRoot(fragment: ResultFragment<T>): Observable<Parcelable> {
        val publishSubject = PublishSubject.create<Parcelable>()
        fragment.resultEmitter = publishSubject
        router.newRootScreen(FragmentScreen{ fragment })
        return publishSubject
    }

    fun startNoteEdit(presenterNoteEntity: PresenterNoteEntity) = startForResult(EditorView.newInstance(presenterNoteEntity))

    fun startNoteEditWithRoot(presenterNoteEntity: PresenterNoteEntity) = startForResultWithRoot(EditorView.newInstance(presenterNoteEntity))
}

