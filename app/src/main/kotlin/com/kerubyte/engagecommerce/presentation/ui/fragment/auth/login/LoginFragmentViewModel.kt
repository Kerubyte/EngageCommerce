package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.infrastructure.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _accountLogin = MutableLiveData<Result<Nothing>>()
    val accountLogin: LiveData<Result<Nothing>>
        get() = _accountLogin

    fun loginUser(email: String, password: String) {

        viewModelScope.launch {
            val result = userRepository.loginUser(email, password)
            _accountLogin.postValue(result)
        }
    }
}