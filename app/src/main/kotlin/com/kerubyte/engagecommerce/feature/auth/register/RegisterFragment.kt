package com.kerubyte.engagecommerce.feature.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.common.util.*
import com.kerubyte.engagecommerce.common.util.Constants.SCREEN_REGISTER
import com.kerubyte.engagecommerce.databinding.FragmentRegisterBinding
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ScreenName(name = SCREEN_REGISTER)
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
        subscribeObserver()
        UserCom.getInstance().trackScreen(this)

        return binding.root
    }


    private fun subscribeObserver() {
        observeRegistrationResult()
        doOnInputTextChange()
        observeNavigateToLogin()
    }

    private fun observeRegistrationResult() {
        registerViewModel.accountCreated.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success ->
                    restartMainActivity()
                is Result.Error.AuthenticationError ->
                    showErrorSnackbar(requireView(), R.string.authentication_error)
                is Result.Error.NetworkError ->
                    showErrorSnackbar(requireView(), R.string.network_error)
            }
        }
    }

    fun createUserAccount() {
        val firstName = binding.inputFirstName.text.toString()
        val lastName = binding.inputLastName.text.toString()
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()

        registerViewModel.createUserAccountWithMarketing(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password
        )
    }

    private fun doOnFirstNameInputChange() {
        val firstNameInput = binding.inputFirstName
        firstNameInput.doOnTextChanged { text, _, _, _ ->
            registerViewModel.validateFirstName(text.toString())
        }
    }

    private fun doOnLastNameInputChange() {
        val lastNameInput = binding.inputLastName
        lastNameInput.doOnTextChanged { text, _, _, _ ->
            registerViewModel.validateLastName(text.toString())
        }
    }

    private fun doOnEmailInputChange() {
        val emailInput = binding.inputEmail
        emailInput.doOnTextChanged { text, _, _, _ ->
            registerViewModel.validateEmail(text.toString())
        }
    }

    private fun doOnPasswordInputChange() {
        val passwordInput = binding.inputPassword
        passwordInput.doOnTextChanged { text, _, _, _ ->
            registerViewModel.validatePassword(text.toString())
        }
    }

    private fun doOnInputTextChange() {
        doOnFirstNameInputChange()
        doOnLastNameInputChange()
        doOnEmailInputChange()
        doOnPasswordInputChange()
    }

    private fun observeNavigateToLogin() {
        registerViewModel.navigate.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                navigate(R.id.loginFragment)
            }
        }
    }

    private fun setBindings() {
        binding.registerFragment = this
        binding.registerViewModel = registerViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}