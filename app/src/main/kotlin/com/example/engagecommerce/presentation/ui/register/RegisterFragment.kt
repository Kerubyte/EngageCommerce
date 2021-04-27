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
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ScreenName(name = "Register")
class RegisterFragment : RootFragment(), View.OnClickListener {

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

    private fun createUser(email: String, password: String, firstName: String, lastName: String) {

        if (!Utils.validateEmailAndPassword(email, password)) {
            binding.editEmailRegister.error = "Required"
            binding.editPasswordRegister.error = "Require at least 6 characters"
            return
        }
        if (!Utils.validateFirstAndLastName(firstName, lastName)) {
            binding.editFirstName.error = "Required"
            binding.editLastName.error = "Required"
            return
        }
        viewModel.auth.createAccount(email, password, firstName, lastName)
    }
}
