package com.kerubyte.engagecommerce.presentation.ui.fragment.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.kerubyte.engagecommerce.MainCoroutineRule
import com.kerubyte.engagecommerce.infrastructure.remote.FakeProductRepository
import com.kerubyte.engagecommerce.infrastructure.remote.FakeUserRepository
import com.kerubyte.engagecommerce.infrastructure.util.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test



@ExperimentalCoroutinesApi
class ProductDetailFragmentViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var productDetailFragmentViewModel: ProductDetailFragmentViewModel
    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var fakeUserRepository: FakeUserRepository

    private val savedStateHandle = mockk<SavedStateHandle>()

    @Before
    fun setUp() {

        every { savedStateHandle.get<String>(FAKE_PRODUCT_SAVED_STATE_KEY) }
            .returns(FAKE_PRODUCT_SAVED_STATE_VALUE)

        fakeProductRepository = FakeProductRepository()
        fakeUserRepository = FakeUserRepository()
        productDetailFragmentViewModel = ProductDetailFragmentViewModel(
            savedStateHandle,
            fakeProductRepository,
            fakeUserRepository
        )
    }

    @Test
    fun getSingleProduct_returnsCorrectProduct() {

        val currentProduct = productDetailFragmentViewModel.currentProduct.getOrAwaitValueTest()
        assertThat(currentProduct.data?.uid).isEqualTo(FAKE_PRODUCT_SAVED_STATE_VALUE)
    }

    @Test
    fun addToCart_addsCorrectProductUid() {

        assertThat(currentUserTest.cart).doesNotContain(FAKE_PRODUCT_SAVED_STATE_VALUE)
        productDetailFragmentViewModel.handleAddToCartClick()

        assertThat(currentUserTest.cart).contains(FAKE_PRODUCT_SAVED_STATE_VALUE)
    }

    @Test
    fun addToCartWhenUserNotLoggedIn_navigateValueEqualsTrue() {

        fakeUserRepository = FakeUserRepository()
        fakeUserRepository.decideShouldReturnError(true)
        productDetailFragmentViewModel = ProductDetailFragmentViewModel(
            savedStateHandle,
            fakeProductRepository,
            fakeUserRepository
        )
        productDetailFragmentViewModel.handleAddToCartClick()

        val currentUser = productDetailFragmentViewModel.currentUser.getOrAwaitValueTest()
        val navigateValue = productDetailFragmentViewModel.navigate.getOrAwaitValueTest()
        assertThat(currentUser is Result.Error).isTrue()
        assertThat(navigateValue.getContentIfNotHandled()).isEqualTo(true)
    }
}