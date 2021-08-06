package com.example.notes.view.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.notes.R
import com.example.notes.presenter.base.baseFragment.BaseView
import com.example.notes.presenter.entities.UserLoginHolder
import com.example.notes.presenter.login.LoginViewModel
import com.example.notes.databinding.FragLoginBinding
import com.example.notes.di.Injector
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class LoginView: BaseView<LoginViewModel>(R.layout.frag_login) {
    companion object {
        fun newInstance() = LoginView()
    }

    @Inject
    override lateinit var viewModel: LoginViewModel

    private var _binding: FragLoginBinding? = null
    private val binding get() = _binding!!

    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component?.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragLoginBinding.inflate(inflater)

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.tvError.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = it
            }
        }

        viewModel.isBtnEnable.observe(viewLifecycleOwner) { isEnable ->
            with(binding) {
                btnSignin.isEnabled = isEnable
                btnLogin.isEnabled = isEnable
            }
        }

        viewModel.hideKeyboard.observe(viewLifecycleOwner) {
            val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }

        with(binding) {
            btnLogin.setOnClickListener {
                viewModel.onLogIn(UserLoginHolder(
                    email = etEmail.text.toString(),
                    password = etPassword.text.toString()
                ))
            }

            btnSignin.setOnClickListener {
                viewModel.onSignIn()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}