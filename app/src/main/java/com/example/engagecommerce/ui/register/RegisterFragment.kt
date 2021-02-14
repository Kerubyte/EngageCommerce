package com.example.engagecommerce.ui.register

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.data.User
import com.example.engagecommerce.databinding.FragmentRegisterBinding
import com.example.engagecommerce.repo.FirebaseAuthentication
import com.user.sdk.UserCom
import com.user.sdk.customer.Customer
import com.user.sdk.customer.CustomerUpdateCallback
import com.user.sdk.customer.RegisterResponse
import com.user.sdk.events.ScreenName

@ScreenName(name = "Register")
class RegisterFragment : RootFragment(), View.OnClickListener {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )
        viewModel = RegisterViewModel()

        viewModel.auth.navigate.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.auth.onDoneNavigating()
                restartMainActivity()
            }
        })

        binding.buttonRegister.setOnClickListener(this)
        binding.textSignInAction.setOnClickListener(this)
        UserCom.getInstance().trackScreen(this)
        return binding.root
    }

    // Create User
    private fun createUser(email: String, password: String, firstName: String, lastName: String) {

        if (!validateForm()) {
            return
        }
        viewModel.auth.createAccount(email, password, firstName, lastName)
    }


    // Validate if the required inputs are not empty
    private fun validateForm(): Boolean {
        var valid = true

        val email = binding.editEmailRegister.text.toString()
        if (TextUtils.isEmpty(email)) {
            binding.editEmailRegister.error = "Required"
            valid = false
        } else {
            binding.editEmailRegister.error = null
        }

        val password = binding.editPasswordRegister.text.toString()
        if (TextUtils.isEmpty(password)) {
            binding.editPasswordRegister.error = "Required"
            valid = false
        } else {
            binding.editPasswordRegister.error = null
        }
        return valid
    }

    // Handle all of the clicks in the fragment
    override fun onClick(v: View) {
        when (v) {
            binding.buttonRegister -> {
                createUser(
                    binding.editEmailRegister.text.trim().toString(),
                    binding.editPasswordRegister.text.trim().toString(),
                    binding.editFirstName.text.trim().toString(),
                    binding.editLastName.text.trim().toString()
                )
            }
            binding.textSignInAction -> {
                navigateToLogin()
            }
        }
    }
}