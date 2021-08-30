package com.kerubyte.engagecommerce.presentation.ui.fragment.detail

import androidx.lifecycle.*
import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.domain.repo.ProductRepository
import com.kerubyte.engagecommerce.domain.repo.UserRepository
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.infrastructure.util.Status
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

    private val _currentProduct = MutableLiveData<Resource<Product>>()
    val currentProduct: LiveData<Resource<Product>>
        get() = _currentProduct

    private val _currentUser = MutableLiveData<Resource<User>>()
    private val currentUser: LiveData<Resource<User>>
        get() = _currentUser

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

    val isNotInCart = Transformations.switchMap(_currentUser) {
        return@switchMap checkForProductInCart()
    }

    private fun getSingleProduct() {

        _currentProduct.value = Resource(Status.LOADING, null, null)

        productUid?.let { uid ->
            viewModelScope.launch {

                val result = productRepository.getSingleProduct(uid)
                _currentProduct.postValue(result)
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

    private fun checkForProductInCart(): LiveData<Boolean> {

        val isNotInCart = MutableLiveData<Boolean>()

        currentUser.value?.data?.cart?.let { cart ->
            isNotInCart.value = productUid !in cart
        }
        return isNotInCart
    }

    fun handleAddToCartClick() {

        currentUser.value?.data?.let {
            addToCart()
            getCurrentUser()
        } ?: navigateToLogin()
    }

    private fun navigateToLogin() {
        _navigate.value = true
    }

    private fun onDoneNavigating() {
        _navigate.value = false
    }

    init {
        getSingleProduct()
        getCurrentUser()
        onDoneNavigating()
    }
}