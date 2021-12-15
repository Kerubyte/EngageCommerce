package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.FragmentLoginBinding
import com.kerubyte.engagecommerce.infrastructure.util.navigate
import com.kerubyte.engagecommerce.infrastructure.util.restartMainActivity
import com.kerubyte.engagecommerce.infrastructure.util.setAnimation
import com.kerubyte.engagecommerce.infrastructure.util.showErrorSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginFragmentViewModel by viewModels()
    lateinit var binding: FragmentLoginBinding

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
        setupObserver()
        setBindings()

        return binding.root
    }

    private fun setupObserver() {

        observeLoginResult()
        doOnEmailInputChange()
        doOnPasswordInputChange()
        observeNavigateToRegister()
    }

    private fun observeLoginResult() {

        loginViewModel.loginResult.observe(viewLifecycleOwner, { result ->

            when (result) {

                is com.kerubyte.engagecommerce.infrastructure.util.Result.Success ->
                    restartMainActivity()
                is com.kerubyte.engagecommerce.infrastructure.util.Result.Error.AuthenticationError ->
                    showErrorSnackbar(requireView(), R.string.authentication_error)
                is com.kerubyte.engagecommerce.infrastructure.util.Result.Error.NetworkError ->
                    showErrorSnackbar(requireView(), R.string.network_error)
            }
        })
    }

    private fun doOnEmailInputChange() {

        val emailInput = binding.inputUserEmail
        emailInput.doOnTextChanged { text, _, _, _ ->
            loginViewModel.validateEmail(text.toString())
        }
    }

    private fun doOnPasswordInputChange() {

        val passwordInput = binding.inputUserPassword
        passwordInput.doOnTextChanged { text, _, _, _ ->
            loginViewModel.validatePassword(text.toString())
        }
    }

    fun loginUser() {

        val userEmail = binding.inputUserEmail.text.toString()
        val userPassword = binding.inputUserPassword.text.toString()

        loginViewModel.loginUser(userEmail, userPassword)
    }

    private fun observeNavigateToRegister() {

        loginViewModel.navigate.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                navigate(R.id.registerFragment)
            }
        })
    }

    private fun setBindings() {
        binding.loginFragment = this
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}