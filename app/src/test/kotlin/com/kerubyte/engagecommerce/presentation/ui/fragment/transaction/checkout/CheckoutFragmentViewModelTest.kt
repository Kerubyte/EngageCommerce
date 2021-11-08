package com.kerubyte.engagecommerce.presentation.ui.fragment.transaction.checkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.kerubyte.engagecommerce.MainCoroutineRule
import com.kerubyte.engagecommerce.infrastructure.remote.FakeOrderRepository
import com.kerubyte.engagecommerce.infrastructure.remote.FakeProductRepository
import com.kerubyte.engagecommerce.infrastructure.remote.FakeUserRepository
import com.kerubyte.engagecommerce.infrastructure.util.FAKE_CART_SAVED_STATE_KEY
import com.kerubyte.engagecommerce.infrastructure.util.FAKE_CART_SAVED_STATE_VALUE
import com.kerubyte.engagecommerce.infrastructure.util.getOrAwaitValueTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CheckoutFragmentViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var checkoutFragmentViewModel: CheckoutFragmentViewModel
    private lateinit var fakeProductRepository: FakeProductRepository
    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var fakeOrderRepository: FakeOrderRepository

    private val savedStateHandle = mockk<SavedStateHandle>()

    @Before
    fun setUp() {

        every { savedStateHandle.get<String>(FAKE_CART_SAVED_STATE_KEY) }
            .returns(FAKE_CART_SAVED_STATE_VALUE)

        fakeProductRepository = FakeProductRepository()
        fakeUserRepository = FakeUserRepository()
        fakeOrderRepository = FakeOrderRepository()
        checkoutFragmentViewModel = CheckoutFragmentViewModel(
            savedStateHandle,
            fakeUserRepository,
            fakeProductRepository,
            fakeOrderRepository
        )
    }

    @Test
    fun placingOrder_clearsCartAndNavigates() {

        val currentUserBefore = checkoutFragmentViewModel.currentUser.getOrAwaitValueTest()
        assertThat(currentUserBefore.data?.cart).isNotEmpty()
        checkoutFragmentViewModel.placeOrder()

        val currentUserAfter = checkoutFragmentViewModel.currentUser.getOrAwaitValueTest()
        val navigateValue = checkoutFragmentViewModel.navigate.getOrAwaitValueTest()
        assertThat(currentUserAfter.data?.cart).isEmpty()
        assertThat(navigateValue.getContentIfNotHandled()).isEqualTo(true)

    }
}