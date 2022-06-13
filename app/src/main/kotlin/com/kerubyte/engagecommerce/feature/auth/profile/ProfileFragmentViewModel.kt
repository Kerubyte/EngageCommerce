package com.kerubyte.engagecommerce.feature.auth.profile

import androidx.lifecycle.*
import com.kerubyte.engagecommerce.common.domain.UserRepository
import com.kerubyte.engagecommerce.common.domain.model.User
import com.kerubyte.engagecommerce.common.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _currentUser = MutableLiveData<Result<User>>()
    val currentUser: LiveData<Result<User>>
        get() = _currentUser

    val userName =
        Transformations.map(currentUser) { result ->
            result.data?.let { user ->
                "${user.firstName} ${user.lastName}"
            }
        }

    val isUserAddressProvided =
        Transformations.map(currentUser) { result ->
            result.data?.address?.values?.isNotEmpty() ?: false
        }

    val userAddress = Transformations.map(currentUser) { it.data?.address }

    private fun getCurrentUser() {

        viewModelScope.launch {
            val result = userRepository.getUserData()
            _currentUser.postValue(result)
        }
    }

    fun updateUserAddress(street: String, postCode: String, city: String, country: String) {

        val userAddress =
            mapOf(
                "street" to street,
                "postCode" to postCode,
                "city" to city,
                "country" to country
            )

        viewModelScope.launch {
            userRepository.updateAddress(userAddress)
            getCurrentUser()
        }
    }

    init {
        getCurrentUser()
    }
}