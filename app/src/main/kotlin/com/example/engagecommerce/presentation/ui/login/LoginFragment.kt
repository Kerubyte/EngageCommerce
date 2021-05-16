package com.example.engagecommerce.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.engagecommerce.R
import com.example.engagecommerce.databinding.FragmentLoginBinding
import com.example.engagecommerce.infrastructure.RootFragment
import com.user.sdk.events.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ScreenName(name = "Login")
class LoginFragment : RootFragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        setAnimation()
        setBindings()
        subscribeObservers()
        trackScreen(this)

        return binding.root
    }

    fun loginUser() {
        loginViewModel.loginUser(
            binding.editLoginEmail.text.trim().toString(),
            binding.editLoginPassword.text.trim().toString()
        )
    }

    private fun subscribeObservers() {
        loginViewModel.navigate.observe(viewLifecycleOwner, {
            if (it) {
                loginViewModel.onDoneNavigating()
                restartMainActivity()
            }
        })
    }

    private fun setBindings() {
        binding.loginFragment = this
        binding.lifecycleOwner = viewLifecycleOwner
    }
}