package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.register

import androidx.lifecycle.ViewModel
import com.kerubyte.engagecommerce.application.utils.auth.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel
    @Inject
    constructor(
        private val inputValidator: InputValidator
    ) : ViewModel() {
}