package com.kerubyte.engagecommerce.presentation.ui

import android.view.Gravity
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.SlideDistanceProvider

abstract class RootFragment : Fragment() {

    fun setAnimation() {
        enterTransition = MaterialFadeThrough().apply {
            secondaryAnimatorProvider = SlideDistanceProvider(Gravity.END)
        }

        exitTransition = MaterialFadeThrough().apply {
            secondaryAnimatorProvider = SlideDistanceProvider(Gravity.START)
        }
    }
}