package com.example.notes.cleanArchitecture.view.signin

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.notes.R
import com.example.notes.classes.base.baseFragment.BaseView
import com.example.notes.cleanArchitecture.presenter.entities.UserLoginHolder
import com.example.notes.cleanArchitecture.presenter.singin.SignInViewModel
import com.example.notes.databinding.FragSigninBinding
import com.example.notes.di.Injector
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class SignInView: BaseView<SignInViewModel>(R.layout.frag_signin) {
    companion object {
        fun newInstance() = SignInView()
    }

    @Inject
    override lateinit var viewModel: SignInViewModel

    private var _binding: FragSigninBinding? = null
    private val binding get() = _binding!!

    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragSigninBinding.inflate(inflater)

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.tvError.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = it
            }
        }

        viewModel.isBtnEnable.observe(viewLifecycleOwner) {
            binding.btnSignin.isEnabled = it
        }

        viewModel.signInData.observe(viewLifecycleOwner) { userData ->
            auth.createUserWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener(requireActivity(), viewModel::onAuthResult)
        }

        viewModel.hideKeyboard.observe(viewLifecycleOwner) {
            val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }

        binding.btnSignin.setOnClickListener {
            viewModel.onSingIn(UserLoginHolder(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            ))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}