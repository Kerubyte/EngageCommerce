package com.example.engagecommerce.presentation.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.engagecommerce.R
import com.example.engagecommerce.application.util.Utils
import com.example.engagecommerce.databinding.FragmentRegisterBinding
import com.example.engagecommerce.infrastructure.RootFragment
import com.user.sdk.events.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ScreenName(name = "Register")
class RegisterFragment : RootFragment() {

    private val viewModel: RegisterViewModel by viewModels()
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
        setAnimation()
        setBindings()
        subscribeObservers()
        trackScreen(this)

        return binding.root
    }

    fun createUser() {

        val email = binding.editEmailRegister.text.trim().toString()
        val password = binding.editPasswordRegister.text.trim().toString()
        val firstName = binding.editFirstName.text.trim().toString()
        val lastName = binding.editLastName.text.trim().toString()

        if (!Utils.validateEmailAndPassword(email, password)) {
            binding.editEmailRegister.error = "Required"
            binding.editPasswordRegister.error = "Requires at least 6 characters"
            return
        }
        if (!Utils.validateFirstAndLastName(firstName, lastName)) {
            binding.editFirstName.error = "Required"
            binding.editLastName.error = "Required"
            return
        }
        viewModel.createUser(email, password, firstName, lastName)
    }

    private fun subscribeObservers() {
        viewModel.navigate.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.onDoneNavigating()
                restartMainActivity()
            }
        })
    }

    private fun setBindings() {
        binding.registerFragment = this
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
