package com.example.engagecommerce.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.databinding.FragmentLoginBinding
import com.example.engagecommerce.repo.FirebaseAuthentication

class LoginFragment : RootFragment(), View.OnClickListener {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModelAuth: FirebaseAuthentication

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

        //Get Repository ViewModel to provide Firebase Auth
        viewModelAuth = FirebaseAuthentication()


        binding.buttonLogin.setOnClickListener(this)
        binding.textSignUpAction.setOnClickListener(this)

        viewModelAuth.navigate.observe(viewLifecycleOwner, {
            if (it) {
                viewModelAuth.onDoneNavigating()
                restartMainActivity()
            }
        })

        return binding.root
    }

    // Login user
    private fun loginUser(email: String, password: String) {
        if (!validateForm()) {
            return
        }
        viewModelAuth.loginUser(email, password)
    }

    // Validate if the required inputs are not empty
    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.editLoginEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.editLoginEmail.error = "Required"
            valid = false
        } else {
            binding.editLoginEmail.error = null
        }

        val password = binding.editLoginPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.editLoginPassword.error = "Required"
            valid = false
        } else {
            binding.editLoginPassword.error = null
        }
        return valid
    }

    // Handle all of the clicks in the fragment
    override fun onClick(v: View?) {
        when (v) {
            binding.buttonLogin ->
                loginUser(
                    binding.editLoginEmail.text.trim().toString(),
                    binding.editLoginPassword.text.trim().toString()
                )
            binding.textSignUpAction ->
                navigateToRegister()
        }
    }
}