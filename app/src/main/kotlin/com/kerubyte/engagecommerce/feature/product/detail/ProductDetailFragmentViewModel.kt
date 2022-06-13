package com.kerubyte.engagecommerce.feature.product.detail

import androidx.lifecycle.*
import com.kerubyte.engagecommerce.common.domain.ProductRepository
import com.kerubyte.engagecommerce.common.domain.UserRepository
import com.kerubyte.engagecommerce.common.domain.model.ProductModel
import com.kerubyte.engagecommerce.common.domain.model.UserModel
import com.kerubyte.engagecommerce.common.util.Event
import com.kerubyte.engagecommerce.common.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailFragmentViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val productUid = savedStateHandle.get<String>("productUid")

    private val _currentProductModel = MutableLiveData<Result<ProductModel>>()
    val currentProductModel: LiveData<Result<ProductModel>>
        get() = _currentProductModel

    private val _currentUser = MutableLiveData<Result<UserModel>>()
    val currentUser: LiveData<Result<UserModel>>
        get() = _currentUser

    private val _navigate = MutableLiveData<Event<Boolean>>()
    val navigate: LiveData<Event<Boolean>>
        get() = _navigate

    val isNotInCart = Transformations.map(_currentUser) {
        it.data?.cart?.let { userCart -> productUid !in userCart } ?: true
    }

    private fun getSingleProduct() {

        productUid?.let { uid ->
            viewModelScope.launch {

                val result = productRepository.getSingleProduct(uid)
                _currentProductModel.postValue(result)
            }
        }
    }

    private fun getCurrentUser() {

        viewModelScope.launch {
            val result = userRepository.getUserData()
            _currentUser.postValue(result)
        }
    }

    private fun addToCart() {

        productUid?.let { uid ->
            viewModelScope.launch {
                userRepository.addToCart(uid)
            }
        }
    }

    fun handleAddToCartClick() {

        currentUser.value?.data?.let {
            addToCart()
            getCurrentUser()
        } ?: navigate()
    }

    private fun navigate() {
        _navigate.value = Event(true)
    }

    init {
        getSingleProduct()
        getCurrentUser()
    }
}