package com.kerubyte.engagecommerce.presentation.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerubyte.engagecommerce.data.repository.MarketingRepository
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityMainViewModel
@Inject
constructor(
    private val userRepository: UserRepository,
    private val marketingRepository: MarketingRepository
) : ViewModel() {

    private val _currentUser = MutableLiveData<Result<User>>()
    val currentUser: LiveData<Result<User>>
        get() = _currentUser

    private fun getCurrentUser() {

        viewModelScope.launch {
            val result = userRepository.getUserData()
            _currentUser.postValue(result)
            result.data?.let { user ->
                registerUserData(user)
            }
        }
    }

    private fun registerUserData(user: User) {
        viewModelScope.launch {
            marketingRepository.registerUser(
                user.uid,
                user.firstName,
                user.lastName,
                user.email
            )
        }
    }

    init {
        getCurrentUser()
    }
}