package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.FragmentRegisterBinding
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.infrastructure.util.restartMainActivity
import com.kerubyte.engagecommerce.infrastructure.util.setAnimation
import com.kerubyte.engagecommerce.infrastructure.util.showErrorSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val registerViewModel: RegisterFragmentViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )

        setAnimation()
        setBindings()
        setupObserver()

        return binding.root
    }


    private fun setupObserver() {

        observeRegistrationResult()
    }

    private fun observeRegistrationResult() {

        registerViewModel.accountCreated.observe(viewLifecycleOwner, {

            when (it) {

                is Resource.Success ->
                    restartMainActivity()
                is Resource.Error.AuthenticationError ->
                    showErrorSnackbar(requireView(), R.string.authentication_error)
                is Resource.Error.NetworkError ->
                    showErrorSnackbar(requireView(), R.string.network_error)
            }
        })
    }

    fun createUserAccount() {

        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        val firstName = binding.inputFirstName.text.toString()
        val lastName = binding.inputLastName.text.toString()

        registerViewModel.createUserAccount(email, password, firstName, lastName)
    }

    private fun setBindings() {
        binding.registerFragment = this
        binding.lifecycleOwner = viewLifecycleOwner
    }
}