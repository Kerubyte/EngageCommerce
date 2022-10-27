package com.kerubyte.engagecommerce.feature.transaction.cart

import androidx.lifecycle.*
import com.kerubyte.engagecommerce.common.domain.ProductRepository
import com.kerubyte.engagecommerce.common.domain.UserRepository
import com.kerubyte.engagecommerce.common.domain.model.ProductModel
import com.kerubyte.engagecommerce.common.domain.model.UserModel
import com.kerubyte.engagecommerce.common.util.Event
import com.kerubyte.engagecommerce.common.util.PriceFormatter
import com.kerubyte.engagecommerce.common.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel
@Inject
constructor(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val priceFormatter: PriceFormatter
) : ViewModel() {

    private val _currentUser = MutableLiveData<Result<UserModel>>()
    val currentUser: LiveData<Result<UserModel>>
        get() = _currentUser

    private val _navigate = MutableLiveData<Event<Boolean>>()
    val navigate: LiveData<Event<Boolean>>
        get() = _navigate

    val productsInCart = Transformations.switchMap(currentUser) { result ->

        result.data?.let { user ->
            getProductsFromCart(user.cart)
        }
    }

    val productsInCartValue = Transformations.map(productsInCart) { result ->
        val cartValue = result.data?.sumOf { product ->
            product.price
        }
        priceFormatter.formatPrice(cartValue)
    }

    val areProductsInCart = Transformations.map(productsInCart) { it.data!!.isNotEmpty() }

    private fun getProductsFromCart(userCart: List<String>): LiveData<Result<List<ProductModel>>> {

        val productsInCart = MutableLiveData<Result<List<ProductModel>>>()

        viewModelScope.launch {
            val products = productRepository.getProductsFromCart(userCart)
            productsInCart.postValue(products)
        }

        return productsInCart
    }

    private fun getCurrentUser() {

        viewModelScope.launch {
            val result = userRepository.getUserData()
            _currentUser.postValue(result)
        }
    }

    fun removeFromCart(productUid: String) {

        viewModelScope.launch {
            userRepository.removeFromCart(productUid)
        }
    }

    fun handleRemoveFromCart(productUid: String) {

        removeFromCart(productUid)
        getCurrentUser()
    }

    fun navigate() {
        _navigate.value = Event(true)
    }

    init {
        getCurrentUser()
    }
}