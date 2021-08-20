package com.kerubyte.engagecommerce.presentation.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.domain.repo.UserRepository
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityMainViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableLiveData<Resource<User>>()
    val currentUser: LiveData<Resource<User>>
        get() = _currentUser

    private fun getCurrentUser() {

        viewModelScope.launch {
            val result = userRepository.getUserData()
            _currentUser.postValue(result)
        }
    }

    init {
        getCurrentUser()
    }
}