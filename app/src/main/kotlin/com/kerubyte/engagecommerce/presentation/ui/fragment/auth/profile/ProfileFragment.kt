package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kerubyte.engagecommerce.R
import com.kerubyte.engagecommerce.databinding.FragmentProfileBinding
import com.kerubyte.engagecommerce.infrastructure.util.setAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileFragmentViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        setAnimation()
        setBindings()

        return binding.root
    }

    fun verifyAndUpdateUserAddress() {

        val editStreet = binding.editAddressStreetName.text.toString().trim()
        val editPostCode = binding.editAddressPostCode.text.toString().trim()
        val editCity = binding.editAddressCity.text.toString().trim()
        val editCountry = binding.editAddressCountry.text.toString().trim()

        profileViewModel.updateUserAddress(editStreet, editPostCode, editCity, editCountry)
    }

    fun showEditAddress() {

        binding.groupTextAddress.isVisible = false
        binding.groupEditAddress.isVisible = true
    }

    private fun setBindings() {
        binding.profileFragment = this
        binding.profileViewModel = profileViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}