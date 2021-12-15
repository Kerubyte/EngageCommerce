package com.kerubyte.engagecommerce.presentation.ui.fragment.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kerubyte.engagecommerce.MainCoroutineRule
import com.kerubyte.engagecommerce.infrastructure.auth.InputValidator
import com.kerubyte.engagecommerce.infrastructure.remote.FakeUserRepository
import com.kerubyte.engagecommerce.infrastructure.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginFragmentViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var loginFragmentViewModel: LoginFragmentViewModel
    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var inputValidator: InputValidator

    @Before
    fun setUp() {

        inputValidator = InputValidator
        fakeUserRepository = FakeUserRepository()
        loginFragmentViewModel = LoginFragmentViewModel(fakeUserRepository, inputValidator)
    }

    @Test
    fun loginUserWithCorrectInputs_loginResultTrue() {

        loginFragmentViewModel.loginUser(FAKE_USER_EMAIL_VALID, FAKE_USER_PASSWORD_VALID)
        val loginResult = loginFragmentViewModel.loginResult.getOrAwaitValueTest()
        assertThat(loginResult is Result.Success).isTrue()
    }

    @Test
    fun loginUserWithInCorrectInputs_loginResultFalse() {

        loginFragmentViewModel.loginUser(FAKE_USER_EMAIL_INVALID, FAKE_USER_PASSWORD_VALID)
        val loginResult = loginFragmentViewModel.loginResult.getOrAwaitValueTest()
        assertThat(loginResult is Result.Success).isFalse()
    }
}