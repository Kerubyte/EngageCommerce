package com.kerubyte.engagecommerce.presentation.ui.fragment.transaction.checkout

import androidx.lifecycle.*
import com.kerubyte.engagecommerce.data.repository.MarketingRepository
import com.kerubyte.engagecommerce.data.repository.OrderRepository
import com.kerubyte.engagecommerce.data.repository.ProductRepository
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.Constants.EVENT_CHECKOUT
import com.kerubyte.engagecommerce.infrastructure.Constants.EVENT_PURCHASE
import com.kerubyte.engagecommerce.infrastructure.util.Event
import com.kerubyte.engagecommerce.infrastructure.util.MarketingEvent
import com.kerubyte.engagecommerce.infrastructure.util.Result
import com.user.sdk.events.ProductEventType
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
    private val marketingRepository: MarketingRepository
) : ViewModel() {

    private val _currentUser = MutableLiveData<Result<User>>()
    val currentUser: LiveData<Result<User>>
        get() = _currentUser

    private val _navigate = MutableLiveData<Event<Boolean>>()
    val navigate: LiveData<Event<Boolean>>
        get() = _navigate

    val cartValue = savedStateHandle.get<String>("cartValue")

    val productsInCart =
        Transformations.switchMap(currentUser) { result ->
            result.data?.let { user ->
                sendProductEvent(user.cart, EVENT_CHECKOUT)
                getProductsFromCart(user.cart)
            }
        }

    val isUserAddressProvided =
        Transformations.map(currentUser) { result ->
            result.data?.address?.values?.isNotEmpty() ?: false
        }

    val userAddress = Transformations.map(currentUser) { result -> result.data?.address }

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

    private fun sendProductEvent(userCart: List<String>, eventType: ProductEventType) {

        viewModelScope.launch {
            userCart.let { list ->
                list.forEach { cartItem ->
                    val product = productRepository.getSingleProduct(cartItem).data
                    product?.let {
                        marketingRepository.sendProductEvent(it.uid, eventType, it)
                    }
                }
            }
        }
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

        currentUser.value?.data?.let { user ->
            sendProductEvent(user.cart, EVENT_PURCHASE)
        }
        cartValue?.let { value ->
            sendMarketingEvent(value)
        }
        createOrder()
        clearUserCart()
        navigate()
    }

    private fun sendMarketingEvent(
        totalRevenue: String
    ) {

        viewModelScope.launch {
            marketingRepository.sendEvent(
                MarketingEvent.EventType.PURCHASE_SUMMARY,
                totalRevenue = totalRevenue
            )
        }
    }

    init {
        getCurrentUser()
    }
}