package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.infrastructure.auth.InputValidator
import com.kerubyte.engagecommerce.infrastructure.auth.InputValidator.isValidName
import com.kerubyte.engagecommerce.infrastructure.util.Event
import com.kerubyte.engagecommerce.infrastructure.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isValidFirstName = MutableLiveData<Boolean>()
    val isValidFirstName: LiveData<Boolean>
        get() = _isValidFirstName

    private val _isValidLastName = MutableLiveData<Boolean>()
    val isValidLastName: LiveData<Boolean>
        get() = _isValidLastName

    private val _isValidEmail = MutableLiveData<Boolean>()
    val isValidEmail: LiveData<Boolean>
        get() = _isValidEmail

    private val _isValidPassword = MutableLiveData<Boolean>()
    val isValidPassword: LiveData<Boolean>
        get() = _isValidPassword

    private val _navigate = MutableLiveData<Event<Boolean>>()
    val navigate: LiveData<Event<Boolean>>
        get() = _navigate

    private val _accountCreated = MutableLiveData<Result<Nothing>>()
    val accountCreated: LiveData<Result<Nothing>>
        get() = _accountCreated

    fun createUserAccount(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {

        if (validateRegisterInputs(firstName, lastName, email, password)) {
            viewModelScope.launch {
                val result = userRepository.createAccount(email, password, firstName, lastName)
                _accountCreated.postValue(result)
            }
        }
    }

    fun validateFirstName(firstName: String) {

        _isValidFirstName.value = isValidName(firstName)
    }

    fun validateLastName(lastName: String) {

        _isValidLastName.value = InputValidator.isValidName(lastName)
    }

    fun validateEmail(email: String) {

        _isValidEmail.value = InputValidator.isValidEmail(email)
    }

    fun validatePassword(password: String) {

        _isValidPassword.value = InputValidator.isValidPassword(password)
    }

    fun navigate() {
        _navigate.value = Event(true)
    }

    private fun validateRegisterInputs(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Boolean {

        return InputValidator.isValidEmail(email)
                && InputValidator.isValidPassword(password)
                && InputValidator.isValidName(firstName)
                && InputValidator.isValidName(lastName)
    }
}