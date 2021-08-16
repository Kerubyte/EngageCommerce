package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.register

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
class RegisterFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _accountCreated = MutableLiveData<Resource<Status>>()
    val accountCreated: LiveData<Resource<Status>>
        get() = _accountCreated

    fun CreateUserAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {

        viewModelScope.launch {
            val result = userRepository.createAccount(email, password, firstName, lastName)
            _accountCreated.postValue(result)
        }
    }


}