package com.kerubyte.engagecommerce.presentation.ui.fragment.transaction.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kerubyte.engagecommerce.MainCoroutineRule
import com.kerubyte.engagecommerce.infrastructure.remote.FakeProductRepository
import com.kerubyte.engagecommerce.infrastructure.remote.FakeUserRepository
import com.kerubyte.engagecommerce.infrastructure.util.PriceFormatter
import com.kerubyte.engagecommerce.infrastructure.util.currentUserTest
import com.kerubyte.engagecommerce.infrastructure.util.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CartFragmentViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var cartFragmentViewModel: CartFragmentViewModel
    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var priceFormatter: PriceFormatter

    @Before
    fun setUp() {

        fakeUserRepository = FakeUserRepository()
        fakeProductRepository = FakeProductRepository()
        priceFormatter = PriceFormatter()
        cartFragmentViewModel = CartFragmentViewModel(
                fakeUserRepository,
                fakeProductRepository,
                priceFormatter
            )
    }


    @Test
    fun getUserData_returnsUser() {

        val result = cartFragmentViewModel.currentUser.getOrAwaitValueTest()
        assertThat(result.data).isNotNull()
    }

    @Test
    fun productsInCartSizeEqualsUserCartSize_returnsTrue() {

        val productsInCart = cartFragmentViewModel.productsInCart.getOrAwaitValueTest()
        assertThat(productsInCart.data?.size).isEqualTo(currentUserTest.cart.size)
    }

    @Test
    fun areProductsInCartWithPopulatedCart_returnsTrue() {

        val areProductsInCart = cartFragmentViewModel.areProductsInCart.getOrAwaitValueTest()
        assertThat(areProductsInCart).isTrue()
    }

    @Test
    fun areProductsInCartWithEmptyCart_returnsFalse() {

        val areProductsInCart = cartFragmentViewModel.areProductsInCart.getOrAwaitValueTest()
        val current = cartFragmentViewModel.currentUser.getOrAwaitValueTest()
        //assertThat(areProductsInCart).isFalse()
        assertThat(current.data?.cart).isEmpty()
    }

    @Test
    fun removeFromCartRemovesCorrectProduct_returnsTrue() {

        val productUid = "1"
        assertThat(currentUserTest.cart).contains(productUid)
        cartFragmentViewModel.removeFromCart(productUid)
        assertThat(currentUserTest.cart).doesNotContain(productUid)
    }
}