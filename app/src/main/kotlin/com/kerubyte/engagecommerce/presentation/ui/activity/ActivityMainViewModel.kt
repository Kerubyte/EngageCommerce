package com.kerubyte.engagecommerce.presentation.ui.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.toObject
import com.kerubyte.engagecommerce.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.infrastructure.util.Result
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

    private val _currentUser = MutableLiveData<Result<User>>()
    val currentUser: LiveData<Result<User>>
        get() = _currentUser

    private val userDocument = userRepository.getCurrentUser()

    private val snapshotListenerRegistration =
        userDocument?.addSnapshotListener { querySnapshot, error ->
            error?.let {

                Log.d("snapshotMain", it.toString())

                return@addSnapshotListener
            }
            querySnapshot?.let { it ->

                val userEntity = it.toObject<DatabaseUser>()
                val currentUser = userMapper.mapFromDatabase(userEntity)
                val cartSizeValue = provideCartSize(currentUser)
                _cartSize.value = cartSizeValue
            }
        }

    private val _cartSize = MutableLiveData<String>()
    val cartSize: LiveData<String>
        get() = _cartSize

    private fun provideCartSize(currentUser: User): String {

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