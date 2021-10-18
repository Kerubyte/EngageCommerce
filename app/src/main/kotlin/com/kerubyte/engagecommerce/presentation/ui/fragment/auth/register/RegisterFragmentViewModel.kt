package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.data.util.DispatcherProvider
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.infrastructure.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _accountCreated = MutableLiveData<Resource<Status>>()
    val accountCreated: LiveData<Resource<Status>>
        get() = _accountCreated

    fun createUserAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {

        _accountCreated.value = Resource(Status.LOADING, null, null)

        viewModelScope.launch(dispatcherProvider.io) {
            val result = userRepository.createAccount(email, password, firstName, lastName)
            _accountCreated.postValue(result)
        }
    }


}