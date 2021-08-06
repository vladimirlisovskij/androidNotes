package com.example.notes.presenter.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.domain.useCases.LoginUseCase
import com.example.notes.presenter.backCoordinator.OnBackCollector
import com.example.notes.presenter.base.baseFragment.BaseViewModel
import com.example.notes.presenter.coordinator.Coordinator
import com.example.notes.presenter.entities.UserLoginHolder
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val coordinator: Coordinator,
    private val backCollector: OnBackCollector,
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage get() = _errorMessage as LiveData<String?>

    private val _isBtnEnable = MutableLiveData<Boolean>()
    val isBtnEnable get() = _isBtnEnable as LiveData<Boolean>

    private val _hideKeyboard = MutableLiveData<Unit>()
    val hideKeyboard get() = _hideKeyboard as LiveData<Unit>

    private val auth = Firebase.auth

    override fun onCreate() {
        super.onCreate()
        backCollector.subscribe {
            coordinator.back()
        }
        if (auth.currentUser != null) {
            coordinator.openListNote()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        backCollector.disposeLastSubscription()
    }

    fun onLogIn(userLoginHolder: UserLoginHolder) {
        when {
            userLoginHolder.email.isBlank() -> {
                _errorMessage.postValue("Email is empty")
            }
            userLoginHolder.password.length < 6 -> {
                _errorMessage.postValue("Password to short")
            }
            else -> {
                _isBtnEnable.postValue(false)
                _hideKeyboard.postValue(Unit)
                loginUseCase(userLoginHolder.email, userLoginHolder.password)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doFinally {
                        _isBtnEnable.postValue(true)
                    }
                    .subscribe(
                        {
                            coordinator.openListNote()
                        },
                        {
                            when (it) {
                                is FirebaseAuthInvalidUserException -> _errorMessage.postValue("Invalid email and password")

                                is FirebaseAuthInvalidCredentialsException -> _errorMessage.postValue("Invalid email")

                                is FirebaseNetworkException -> _errorMessage.postValue("Invalid network")

                                is FirebaseException -> _errorMessage.postValue("Invalid network or use vpn")

                                else -> _errorMessage.postValue(it.toString())
                            }
                        }
                    )
            }
        }
    }

    fun onSignIn() {
        coordinator.openSignIn()
    }
}