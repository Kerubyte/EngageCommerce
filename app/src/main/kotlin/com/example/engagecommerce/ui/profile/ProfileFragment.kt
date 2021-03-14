package com.example.engagecommerce.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.engagecommerce.R
import com.example.engagecommerce.RootFragment
import com.example.engagecommerce.databinding.FragmentProfileBinding
import com.user.sdk.UserCom
import com.user.sdk.events.ScreenName

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
        setAnimation()

        profileViewModel.navigate.observe(viewLifecycleOwner, {
            if (it) {
                profileViewModel.onDoneNavigating()
                restartMainActivity()
            }
        })

        binding.profileViewModel = profileViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.buttonSignOut.setOnClickListener(this)
        UserCom.getInstance().trackScreen(this)
        return binding.root
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.buttonSignOut -> {
                profileViewModel.signOut()
            }
        }
    }
}