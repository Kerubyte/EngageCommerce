package com.example.engagecommerce.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.engagecommerce.MainActivity
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.data.User
import com.example.engagecommerce.databinding.FragmentProfileBinding
import com.example.engagecommerce.repo.FirebaseAuthentication
import java.util.*

class ProfileFragment : RootFragment(), View.OnClickListener {

    private val profileViewModel by viewModels<ProfileViewModel>()
    private lateinit var viewModelAuth: FirebaseAuthentication
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )
        viewModelAuth = FirebaseAuthentication()

        viewModelAuth.navigate.observe(viewLifecycleOwner, {
            if (it) {
                viewModelAuth.onDoneNavigating()
                restartMainActivity()
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        profileViewModel.user?.observe(viewLifecycleOwner, { user ->
            bindUserData(user)
        })

        binding.buttonSignOut.setOnClickListener(this)
    }

    private fun bindUserData(user: User) {
        val locale = Locale.getDefault()
        binding.textFirstNameValue.text = user.firstName?.capitalize(locale)
        binding.textLastNameValue.text = user.lastName?.capitalize(locale)
        binding.textEmailValue.text = user.email?.capitalize(locale)
    }

    // Handle all of the clicks in the fragment
    override fun onClick(view: View?) {
        when (view) {
            binding.buttonSignOut -> {
                viewModelAuth.signOut()
            }
        }
    }
}