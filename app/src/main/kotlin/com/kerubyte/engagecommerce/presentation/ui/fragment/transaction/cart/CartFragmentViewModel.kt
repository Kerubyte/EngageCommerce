package com.kerubyte.engagecommerce.presentation.ui.fragment.transaction.cart

import androidx.lifecycle.*
import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.domain.repo.ProductRepository
import com.kerubyte.engagecommerce.domain.repo.UserRepository
import com.kerubyte.engagecommerce.infrastructure.util.PriceFormatter
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.infrastructure.util.Status
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

    private val _currentUser = MutableLiveData<Resource<User>>()

    val productsInCart = Transformations.switchMap(_currentUser) {

        it.data?.let { user ->
            getProductsFromCart(user.cart)
        }
    }

    val userCartValue = Transformations.map(productsInCart) {

        val cartValue = it.data?.map { product ->
            product.price
        }?.sum()
        priceFormatter.formatPrice(cartValue)
    }

    val userCartSize = Transformations.map(productsInCart) { it.data!!.size.toString() }

    val areProductsInCart = Transformations.map(productsInCart) { it.data!!.isNotEmpty() }

    private fun getProductsFromCart(userCart: List<String>): LiveData<Resource<List<Product>>> {

        val productsInCart = MutableLiveData<Resource<List<Product>>>()

        if (userCart.isEmpty()) {
            productsInCart.value = Resource(Status.SUCCESS, emptyList(), null)
        } else {
            viewModelScope.launch {
                val products = productRepository.getProductsFromCart(userCart)
                productsInCart.postValue(products)
            }
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

    init {
        getCurrentUser()
    }
}