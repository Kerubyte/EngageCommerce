package com.kerubyte.engagecommerce.presentation.ui.fragment.title

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kerubyte.engagecommerce.MainCoroutineRule
import com.kerubyte.engagecommerce.infrastructure.remote.FakeProductRepository
import com.kerubyte.engagecommerce.infrastructure.util.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TitleFragmentViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var titleFragmentViewModel: TitleFragmentViewModel
    private lateinit var fakeProductRepository: FakeProductRepository


    @Before
    fun setUp() {

        fakeProductRepository = FakeProductRepository()
        titleFragmentViewModel = TitleFragmentViewModel(fakeProductRepository)
    }

    @Test
    fun getAllProducts_ReturnListOfProduct() {

        val result = titleFragmentViewModel.products.getOrAwaitValueTest()
        assertThat(result is com.kerubyte.engagecommerce.infrastructure.util.Result.Success).isTrue()
    }
}