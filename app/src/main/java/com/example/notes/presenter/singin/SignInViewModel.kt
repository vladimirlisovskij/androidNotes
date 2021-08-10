package com.example.notes.presenter.singin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.presenter.base.baseFragment.BaseViewModel
import com.example.notes.domain.useCases.RegisterUseCase
import com.example.notes.presenter.backCoordinator.OnBackCollector
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.entities.UserLoginHolder
import com.example.notes.presenter.progressbarManager.ProgressbarManager
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val coordinator: Coordinator,
    private val backCollector: OnBackCollector,
    private val registerUseCase: RegisterUseCase,
    private val progressbarManager: ProgressbarManager
): BaseViewModel(backCollector, coordinator) {
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage get() = _errorMessage as LiveData<String?>

    private val _hideKeyboard = MutableLiveData<Unit>()
    val hideKeyboard get() = _hideKeyboard as LiveData<Unit>

    fun onSingIn(userLoginHolder: UserLoginHolder) {
        when {
            userLoginHolder.email.isBlank() -> {
                _errorMessage.postValue("Email is empty")
            }
            userLoginHolder.password.length < 6 -> {
                _errorMessage.postValue("Password to short")
            }
            else -> {
                progressbarManager.show()
                _hideKeyboard.postValue(Unit)
                registerUseCase(userLoginHolder.email, userLoginHolder.password)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doFinally {
                        progressbarManager.dismiss()
                    }
                    .subscribe(
                        {
                            coordinator.back()
                            makeToast("Registration success")
                        },
                        {
                            when (it) {
                                is FirebaseAuthInvalidUserException -> _errorMessage.postValue("Invalid email and password")

                                is FirebaseAuthInvalidCredentialsException -> _errorMessage.postValue("Invalid email")

                                is FirebaseAuthUserCollisionException -> _errorMessage.postValue("Email already use")

                                is FirebaseNetworkException -> _errorMessage.postValue("Invalid network")

                                is FirebaseException -> _errorMessage.postValue("Invalid network or use vpn")

                                else -> _errorMessage.postValue(it.toString())
                            }
                        }
                    )
            }
        }
    }
}