package com.example.engagecommerce.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.data.User
import com.example.engagecommerce.databinding.FragmentProfileBinding
import com.example.engagecommerce.repo.FirebaseAuthentication
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName
import com.user.sdk.events.UserComEvent
import java.util.*

@ScreenName(name = "Profile")
class ProfileFragment : RootFragment(), View.OnClickListener {

    private val profileViewModel by viewModels<ProfileViewModel>()
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

        // Observe Live Data to restart Activity on logout
        profileViewModel.auth.navigate.observe(viewLifecycleOwner, {
            if (it) {
                profileViewModel.auth.onDoneNavigating()
                restartMainActivity()
            }
        })

        // Observe current user to bind data to the view
        profileViewModel.user?.observe(viewLifecycleOwner, { user ->
            bindUserData(user)
        })

        binding.buttonSignOut.setOnClickListener(this)
        UserCom.getInstance().trackScreen(this)
        return binding.root
    }

    // Handle all of the clicks in the fragment
    override fun onClick(view: View?) {
        when (view) {
            binding.buttonSignOut -> {
                profileViewModel.auth.signOut()
            }
        }
    }

    private fun bindUserData(user: User) {
        val locale = Locale.getDefault()
        binding.textFirstNameValueProfile.text = user.firstName?.capitalize(locale)
        binding.textLastNameValueProfile.text = user.lastName?.capitalize(locale)
        binding.textEmailValueProfile.text = user.email?.capitalize(locale)
    }
}