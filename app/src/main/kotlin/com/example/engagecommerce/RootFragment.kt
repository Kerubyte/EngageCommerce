package com.example.engagecommerce

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.engagecommerce.ui.activity.MainActivity
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

    fun navigateToCheckout(cartValue: String) {
        findNavController().navigate(
            CartFragmentDirections.actionMenuCartToCheckoutFragment(cartValue)
        )
    }

}
