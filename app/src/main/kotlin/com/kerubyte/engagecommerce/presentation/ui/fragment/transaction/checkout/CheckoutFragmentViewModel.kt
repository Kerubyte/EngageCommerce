package com.kerubyte.engagecommerce.presentation.ui.fragment.transaction.checkout

import androidx.lifecycle.*
import com.kerubyte.engagecommerce.data.repository.OrderRepository
import com.kerubyte.engagecommerce.data.repository.ProductRepository
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.data.util.DispatcherProvider
import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.util.Event
import com.kerubyte.engagecommerce.infrastructure.util.Resource
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
    private val orderRepository: OrderRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _currentUser = MutableLiveData<Resource<User>>()
    private val currentUser: LiveData<Resource<User>>
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

    private fun getProductsFromCart(userCart: List<String>): LiveData<Resource<List<Product>>> {

        val productsInCart = MutableLiveData<Resource<List<Product>>>()

        viewModelScope.launch(dispatcherProvider.io) {
            val products = productRepository.getProductsFromCart(userCart)
            productsInCart.postValue(products)
        }
        return productsInCart
    }

    private fun getCurrentUser() {

        viewModelScope.launch(dispatcherProvider.io) {
            val result = userRepository.getUserData()
            _currentUser.postValue(result)
        }
    }

    private fun createOrder() {

        currentUser.value?.data?.let { user ->
            viewModelScope.launch(dispatcherProvider.io) {

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

        viewModelScope.launch(dispatcherProvider.io) {
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

        viewModelScope.launch(dispatcherProvider.io) {
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