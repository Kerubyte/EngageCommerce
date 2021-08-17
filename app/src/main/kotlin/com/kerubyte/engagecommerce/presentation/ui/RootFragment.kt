package com.kerubyte.engagecommerce.presentation.ui

import android.content.Intent
import android.view.Gravity
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.SlideDistanceProvider
import com.kerubyte.engagecommerce.presentation.ui.activity.MainActivity

abstract class RootFragment : Fragment() {

    fun restartMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
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