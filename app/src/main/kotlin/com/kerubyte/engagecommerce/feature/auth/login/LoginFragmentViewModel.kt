package com.kerubyte.engagecommerce.feature.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerubyte.engagecommerce.feature.auth.data.util.InputValidator
import com.kerubyte.engagecommerce.common.util.Event
import com.kerubyte.engagecommerce.common.util.Result
import com.kerubyte.engagecommerce.feature.auth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel
@Inject
constructor(
    private val authRepository: AuthRepository,
    private val inputValidator: InputValidator
) : ViewModel() {

    private val _isValidEmail = MutableLiveData<Boolean>()
    val isValidEmail: LiveData<Boolean>
        get() = _isValidEmail

    private val _isValidPassword = MutableLiveData<Boolean>()
    val isValidPassword: LiveData<Boolean>
        get() = _isValidPassword

    private val _loginResult = MutableLiveData<Result<Any>>()
    val loginResult: LiveData<Result<Any>>
        get() = _loginResult

    private val _navigate = MutableLiveData<Event<Boolean>>()
    val navigate: LiveData<Event<Boolean>>
        get() = _navigate

    fun loginUser(email: String, password: String) {

        if (validateLoginInputs(email, password)) {

            viewModelScope.launch {
                val result = authRepository.loginUser(email, password)
                _loginResult.postValue(result)
            }
                }else {
            _loginResult.value = Result.Error.AuthenticationError(null)
        }
    }

    fun validateEmail(email: String) {

        _isValidEmail.value = inputValidator.isValidEmail(email)
    }

    fun validatePassword(password: String) {

        _isValidPassword.value = inputValidator.isValidPassword(password)
    }

    fun navigate() {
        _navigate.value = Event(true)
    }

    private fun validateLoginInputs(email: String, password: String): Boolean {

        return inputValidator.isValidEmail(email) && inputValidator.isValidPassword(password)
    }
}