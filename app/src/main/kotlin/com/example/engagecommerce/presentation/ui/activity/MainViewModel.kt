package com.example.engagecommerce.presentation.ui.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.engagecommerce.data.database.UserRepository
import com.example.engagecommerce.data.database.mappers.NullableInputUserEntityMapper
import com.example.engagecommerce.data.entity.UserEntity
import com.example.engagecommerce.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    userRepository: UserRepository,
    private val auth: FirebaseAuth,
    private val userEntityMapper: NullableInputUserEntityMapper
) : ViewModel() {

    val currentUser = userRepository.getUserData()
    private val userDocument = userRepository.getCurrentUser()

    private val snapshotListenerRegistration =
        userDocument?.addSnapshotListener { querySnapshot, error ->
            error?.let {

                Log.d("snapshotMain", it.toString())

                return@addSnapshotListener
            }
            querySnapshot?.let { it ->

                val userEntity = it.toObject<UserEntity>()
                val currentUser = userEntityMapper.mapFromEntity(userEntity)
                val cartSizeValue = provideCartSize(currentUser)
                _cartSize.value = cartSizeValue
            }
        }

    private val _cartSize = MutableLiveData<String>()
    val cartSize: LiveData<String>
        get() = _cartSize

    fun isUserLoggedIn(): Boolean {
        return if (auth.currentUser != null) true
        else return false
    }

    private fun provideCartSize(currentUser: User): String {

        val userCart = currentUser.cart.size
        return if (userCart == 0) "0"
        else userCart.toString()
    }

    override fun onCleared() {
        super.onCleared()
        snapshotListenerRegistration?.remove()
    }
}