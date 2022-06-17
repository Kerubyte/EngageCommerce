package com.kerubyte.engagecommerce.feature.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerubyte.engagecommerce.common.data.mapper.marketing.OutputEventAttributesMapper
import com.kerubyte.engagecommerce.common.domain.MarketingRepository
import com.kerubyte.engagecommerce.common.domain.model.UserModel
import com.kerubyte.engagecommerce.common.util.Event
import com.kerubyte.engagecommerce.common.util.MarketingEvent
import com.kerubyte.engagecommerce.common.util.Result
import com.kerubyte.engagecommerce.feature.auth.data.util.InputValidator
import com.kerubyte.engagecommerce.feature.auth.data.util.InputValidator.isValidName
import com.kerubyte.engagecommerce.feature.auth.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModel
@Inject
constructor(
    private val authRepository: AuthRepository,
    private val marketingRepository: MarketingRepository,
    private val eventAttributesMapper: OutputEventAttributesMapper
) : ViewModel() {

    private val _isValidFirstName = MutableLiveData<Boolean>()
    val isValidFirstName: LiveData<Boolean>
        get() = _isValidFirstName

    private val _isValidLastName = MutableLiveData<Boolean>()
    val isValidLastName: LiveData<Boolean>
        get() = _isValidLastName

    private val _isValidEmail = MutableLiveData<Boolean>()
    val isValidEmail: LiveData<Boolean>
        get() = _isValidEmail

    private val _isValidPassword = MutableLiveData<Boolean>()
    val isValidPassword: LiveData<Boolean>
        get() = _isValidPassword

    private val _navigate = MutableLiveData<Event<Boolean>>()
    val navigate: LiveData<Event<Boolean>>
        get() = _navigate

    private val _accountCreated = MutableLiveData<Result<Any>>()
    val accountCreated: LiveData<Result<Any>>
        get() = _accountCreated

    fun createUserAccount(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        if (validateRegisterInputs(firstName, lastName, email, password)) {
            viewModelScope.launch {
                val result = authRepository.createAccount(email, password, firstName, lastName)
                _accountCreated.postValue(result)
                registerCustomer(result)
                sendRegistrationEvent(result)
            }
        }
    }

    private suspend fun registerCustomer(result: Result<Any>) {
        if (result is Result.Success) {
            result.data?.let { userModel ->
                marketingRepository.registerCustomer(userModel as UserModel)
            }
        }
    }

    private suspend fun sendRegistrationEvent(result: Result<Any>) {
        if (result is Result.Success) {
            result.data?.let { userModel ->
                marketingRepository.sendMarketingEvent(
                    makeRegistrationEvent(userModel as UserModel)
                )
            }
        }
    }

    private fun makeRegistrationEvent(userModel: UserModel): MarketingEvent {
        return MarketingEvent.Registration(
            eventAttributesMapper.mapFromRegistrationInput(
                firstName = userModel.firstName,
                lastName = userModel.lastName,
                email = userModel.email
            )
        )
    }

    fun validateFirstName(firstName: String) {
        _isValidFirstName.value = isValidName(firstName)
    }

    fun validateLastName(lastName: String) {
        _isValidLastName.value = isValidName(lastName)
    }

    fun validateEmail(email: String) {
        _isValidEmail.value = InputValidator.isValidEmail(email)
    }

    fun validatePassword(password: String) {
        _isValidPassword.value = InputValidator.isValidPassword(password)
    }

    fun navigate() {
        _navigate.value = Event(true)
    }

    private fun validateRegisterInputs(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Boolean {
        return InputValidator.isValidEmail(email)
                && InputValidator.isValidPassword(password)
                && isValidName(firstName)
                && isValidName(lastName)
    }
}