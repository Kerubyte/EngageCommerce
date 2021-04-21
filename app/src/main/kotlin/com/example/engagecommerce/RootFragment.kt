package com.example.engagecommerce

import android.content.Intent
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.engagecommerce.ui.activity.MainActivity
import com.example.engagecommerce.ui.transaction.cart.CartFragmentDirections
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.SlideDistanceProvider

abstract class RootFragment : Fragment() {

    fun restartMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

    fun navigateToRegister() {
        Navigation.findNavController(requireView())
            .navigate(
                R.id.registerFragment
            )
    }

    fun navigateToLogin() {
        Navigation.findNavController(requireView())
            .navigate(
                R.id.loginFragment
            )
    }

    fun navigateToCheckout(cartValue: String) {
        findNavController().navigate(
            CartFragmentDirections.actionMenuCartToCheckoutFragment(cartValue)
        )
    }

    fun setAnimation() {
        enterTransition = MaterialFadeThrough().apply {
            secondaryAnimatorProvider = SlideDistanceProvider(Gravity.END)
        }

        exitTransition = MaterialFadeThrough().apply {
            secondaryAnimatorProvider = SlideDistanceProvider(Gravity.START)
        }
    }
}
