package com.example.engagecommerce

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.engagecommerce.ui.transaction.cart.CartFragmentDirections

abstract class RootFragment : Fragment() {


    fun restartMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

    fun navigateToRegister() {
        Navigation.findNavController(requireView())
            .navigate(R.id.menuRegister)
    }

    fun navigateToLogin() {
        Navigation.findNavController(requireView())
            .navigate(R.id.menuLogin)
    }

    fun navigateToCheckout() {
        Navigation.findNavController(requireView())
            .navigate(R.id.action_menuCart_to_checkoutFragment)
    }

    fun navvvv(cartValue: String) {
        findNavController().navigate(
            CartFragmentDirections.actionMenuCartToCheckoutFragment(cartValue)
        )
    }


}
