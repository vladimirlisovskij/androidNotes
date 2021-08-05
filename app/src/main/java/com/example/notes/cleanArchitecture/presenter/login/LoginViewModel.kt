package com.example.notes.cleanArchitecture.presenter.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.classes.backCoordinator.OnBackCollector
import com.example.notes.classes.base.baseFragment.BaseViewModel
import com.example.notes.classes.coordinator.Coordinator
import com.example.notes.cleanArchitecture.presenter.entities.UserLoginHolder
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val coordinator: Coordinator,
    private val backCollector: OnBackCollector
): BaseViewModel() {
    private val _loginData = MutableLiveData<UserLoginHolder>()
    val loginData get() = _loginData as LiveData<UserLoginHolder>

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
                _loginData.postValue(userLoginHolder)
            }
        }
    }

    fun onSignIn() {
        coordinator.openSignIn()
    }

    fun onAuthResult(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            coordinator.openListNote()
            _isBtnEnable.postValue(true)
            _hideKeyboard.postValue(Unit)
        } else {
            when(task.exception) {
                is FirebaseAuthInvalidUserException -> _errorMessage.postValue("Invalid email and password")

                is FirebaseAuthInvalidCredentialsException -> _errorMessage.postValue("Invalid email")

                is FirebaseNetworkException -> _errorMessage.postValue("Invalid network")

                else -> _errorMessage.postValue(task.exception.toString())
            }
            _isBtnEnable.postValue(true)
        }
    }

}