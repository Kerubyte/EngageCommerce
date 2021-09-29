package com.kerubyte.engagecommerce.infrastructure.util

import android.content.Intent
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.SlideDistanceProvider
import com.kerubyte.engagecommerce.presentation.ui.activity.MainActivity

fun Fragment.navigate(fragmentId: Int) {
    Navigation.findNavController(requireView())
        .navigate(
            fragmentId
        )
}

fun Fragment.navigateWithArgs(navDirections: NavDirections) {
    findNavController().navigate(
        navDirections
    )
}

fun Fragment.setAnimation() {
    enterTransition = MaterialFadeThrough().apply {
        secondaryAnimatorProvider = SlideDistanceProvider(Gravity.END)
    }

    exitTransition = MaterialFadeThrough().apply {
        secondaryAnimatorProvider = SlideDistanceProvider(Gravity.START)
    }
}

fun Fragment.restartMainActivity() {
    val intent = Intent(requireContext(), MainActivity::class.java).apply {
        flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
    startActivity(intent)
}