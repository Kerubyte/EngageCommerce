package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.FragmentLoginBinding
import com.kerubyte.engagecommerce.infrastructure.util.Status
import com.kerubyte.engagecommerce.presentation.ui.RootFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : RootFragment() {

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
        loginViewModel.accountLogin.observe(viewLifecycleOwner, {

            when (it.status) {

                Status.LOADING -> Log.d("loginFragment", "loado!")
                Status.SUCCESS -> restartMainActivity()
                Status.ERROR -> Log.d("loginFragment", "eror!")
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