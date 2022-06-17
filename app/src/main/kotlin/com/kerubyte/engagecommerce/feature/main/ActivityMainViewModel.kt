package com.kerubyte.engagecommerce.feature.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.toObject
import com.kerubyte.engagecommerce.common.data.entity.UserEntity
import com.kerubyte.engagecommerce.common.domain.UserRepository
import com.kerubyte.engagecommerce.common.domain.model.UserModel
import com.kerubyte.engagecommerce.common.data.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.common.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityMainViewModel
@Inject
constructor(
    private val userRepository: UserRepository,
    private val userMapper: NullableInputDatabaseUserMapper
) : ViewModel() {

    private val _currentUser = MutableLiveData<Result<UserModel>>()
    val currentUser: LiveData<Result<UserModel>>
        get() = _currentUser

    private val userDocument = userRepository.getCurrentUser()

    private val snapshotListenerRegistration =
        userDocument?.addSnapshotListener { querySnapshot, error ->
            error?.let {

                Log.d("snapshotMain", it.toString())

                return@addSnapshotListener
            }
            querySnapshot?.let { it ->

                val userEntity = it.toObject<UserEntity>()
                val currentUser = userMapper.mapFromDatabase(userEntity)
                val cartSizeValue = provideCartSize(currentUser)
                _cartSize.value = cartSizeValue
            }
        }

    private val _cartSize = MutableLiveData<String>()
    val cartSize: LiveData<String>
        get() = _cartSize

    private fun provideCartSize(currentUser: UserModel): String {

        val userCart = currentUser.cart.size
        return if (userCart == 0) "0"
        else userCart.toString()
    }

    override fun onCleared() {
        super.onCleared()
        snapshotListenerRegistration?.remove()
    }

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