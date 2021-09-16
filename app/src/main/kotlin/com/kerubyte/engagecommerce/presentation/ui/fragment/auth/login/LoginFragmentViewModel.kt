package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerubyte.engagecommerce.domain.repo.UserRepository
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.infrastructure.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _accountLogin = MutableLiveData<Resource<Status>>()
    val accountLogin: LiveData<Resource<Status>>
        get() = _accountLogin

    fun loginUser(email: String, password: String) {

        _accountLogin.value = Resource(Status.LOADING, null, null)

        viewModelScope.launch {
            val result = userRepository.loginUser(email, password)
            _accountLogin.postValue(result)
        }
    }
}