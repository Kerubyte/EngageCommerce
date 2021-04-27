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
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ScreenName(name = "Login")
class LoginFragment : RootFragment(), View.OnClickListener {

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

        loginViewModel.navigate.observe(viewLifecycleOwner, {
            if (it) {
                loginViewModel.onDoneNavigating()
                restartMainActivity()
            }
        })

        binding.buttonLogin.setOnClickListener(this)

        UserCom.getInstance().trackScreen(this)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    override fun onClick(v: View?) {
        when (v) {
            binding.buttonLogin ->
                loginViewModel.loginUser(
                    binding.editLoginEmail.text.trim().toString(),
                    binding.editLoginPassword.text.trim().toString()

                )
            binding.textSignUpAction ->
                navigateToRegister()
        }
    }/*

    private fun loginUser(email: String, password: String) {
        if (!Utils.validateEmailAndPassword(email, password)) {
            val errorMessage = "Invalid Email or Password"
            notify(errorMessage)
            return
        }
        loginViewModel.loginUser2(email, password)
    }*/
}