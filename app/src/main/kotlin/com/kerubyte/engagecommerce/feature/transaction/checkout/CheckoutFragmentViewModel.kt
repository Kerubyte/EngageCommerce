package com.kerubyte.engagecommerce.feature.transaction.checkout

import androidx.lifecycle.*
import com.kerubyte.engagecommerce.common.domain.OrderRepository
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
class CheckoutFragmentViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _currentUser = MutableLiveData<Result<UserModel>>()
    val currentUser: LiveData<Result<UserModel>>
        get() = _currentUser

    private val _navigate = MutableLiveData<Event<Boolean>>()
    val navigate: LiveData<Event<Boolean>>
        get() = _navigate

    val cartValue = savedStateHandle.get<String>("cartValue")

    val productsInCart =
        Transformations.switchMap(currentUser) {
            it.data?.let { user ->
                getProductsFromCart(user.cart)
            }
        }

    val isUserAddressProvided =
        Transformations.map(currentUser) {
            currentUser.value?.data?.address?.values?.isNotEmpty() ?: false
        }

    val userAddress = Transformations.map(currentUser) { it.data?.address }

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

    private fun createOrder() {

        currentUser.value?.data?.let { user ->
            viewModelScope.launch {

                cartValue?.let { value ->

                    val order =
                        mapOf(
                            "products" to user.cart,
                            "value" to value
                        )
                    orderRepository.createOrder(order)
                }
            }
        }
    }

    private fun clearUserCart() {

        viewModelScope.launch {
            userRepository.clearUserCart()
        }
    }

    private fun navigate() {
        _navigate.value = Event(true)
    }

    fun updateUserAddress(street: String, postCode: String, city: String, country: String) {

        val userAddress =
            mapOf(
                "street" to street,
                "postCode" to postCode,
                "city" to city,
                "country" to country
            )

        viewModelScope.launch {
            userRepository.updateAddress(userAddress)
            getCurrentUser()
        }
    }

    fun placeOrder() {

        createOrder()
        clearUserCart()
        navigate()
    }

    init {
        getCurrentUser()
    }
}