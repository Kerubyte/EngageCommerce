package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.FragmentLoginBinding
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
    }

    private fun observeLoginResult() {

        loginViewModel.accountLogin.observe(viewLifecycleOwner, {

            when (it) {

                is com.kerubyte.engagecommerce.infrastructure.util.Result.Success ->
                    restartMainActivity()
                is com.kerubyte.engagecommerce.infrastructure.util.Result.Error.AuthenticationError ->
                    showErrorSnackbar(requireView(), R.string.authentication_error)
                is com.kerubyte.engagecommerce.infrastructure.util.Result.Error.NetworkError ->
                    showErrorSnackbar(requireView(), R.string.network_error)
            }
        })
    }

    fun loginUser() {

        val userEmail = binding.inputUserEmail.text.toString()
        val userPassword = binding.inputUserPassword.text.toString()

        loginViewModel.loginUser(userEmail, userPassword)
    }

    private fun setBindings() {
        binding.loginFragment = this
        binding.lifecycleOwner = viewLifecycleOwner
    }
}