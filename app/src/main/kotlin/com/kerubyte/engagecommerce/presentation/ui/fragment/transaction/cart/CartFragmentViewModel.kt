package com.kerubyte.engagecommerce.presentation.ui.fragment.transaction.cart

import androidx.lifecycle.*
import com.kerubyte.engagecommerce.data.repository.ProductRepository
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.util.Event
import com.kerubyte.engagecommerce.infrastructure.util.PriceFormatter
import com.kerubyte.engagecommerce.infrastructure.util.Result
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

    private val _currentUser = MutableLiveData<Result<User>>()
    val currentUser: LiveData<Result<User>>
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

        val cartValue = result.data?.map { product ->
            product.price
        }?.sum()

        priceFormatter.formatPrice(cartValue)
    }

    val areProductsInCart = Transformations.map(productsInCart) { it.data!!.isNotEmpty() }

    private fun getProductsFromCart(userCart: List<String>): LiveData<Result<List<Product>>> {

        val productsInCart = MutableLiveData<Result<List<Product>>>()

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
            getCurrentUser()
        }
    }

    fun navigate() {
        _navigate.value = Event(true)
    }

    init {
        getCurrentUser()
    }
}