package com.example.engagecommerce.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.engagecommerce.data.database.UserRepository
import com.example.engagecommerce.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val navigate: LiveData<Boolean>
        get() = userRepository.navigate

    val currentUserEntity: LiveData<User>?
        get() = userRepository.getUserData()

    fun onDoneNavigating() {
        userRepository.onDoneNavigating()
    }

    fun signOut() {
        userRepository.signOut()
    }

    init {
        userRepository.getUserData()
    }
}