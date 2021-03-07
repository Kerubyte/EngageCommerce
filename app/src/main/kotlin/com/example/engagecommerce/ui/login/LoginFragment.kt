package com.example.engagecommerce.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.databinding.FragmentLoginBinding
import com.example.engagecommerce.repo.FirebaseAuthentication
import com.example.engagecommerce.utils.Utils
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName

@ScreenName(name = "Login")
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
        setAnimation()

        viewModelAuth = FirebaseAuthentication()

        viewModelAuth.navigate.observe(viewLifecycleOwner, {
            if (it) {
                viewModelAuth.onDoneNavigating()
                restartMainActivity()
            }
        })

        binding.buttonLogin.setOnClickListener(this)
        binding.textSignUpAction.setOnClickListener(this)
        UserCom.getInstance().trackScreen(this)
        return binding.root
    }

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

    private fun loginUser(email: String, password: String) {
        if (!Utils.validateFirstAndLastName(email, password)) {
            binding.editLoginEmail.error = "Required"
            binding.editLoginPassword.error = "Required"
            return
        }
        viewModelAuth.loginUser(email, password)
    }
}