package com.kerubyte.engagecommerce.common.util

import com.kerubyte.engagecommerce.common.data.mapper.marketing.OutputCustomerMapper
import com.kerubyte.engagecommerce.common.data.mapper.marketing.OutputEventAttributesMapper
import com.kerubyte.engagecommerce.common.domain.MarketingRepository
import com.kerubyte.engagecommerce.common.domain.model.ProductModel
import com.kerubyte.engagecommerce.common.domain.model.UserModel
import com.user.sdk.customer.Customer
import com.user.sdk.events.ProductEvent
import com.user.sdk.events.ProductEventType
import javax.inject.Inject

class MarketingUtil
@Inject
constructor(
    private val marketingRepository: MarketingRepository,
    private val outputCustomerMapper: OutputCustomerMapper,
    private val eventAttributesMapper: OutputEventAttributesMapper
) {

    suspend fun registerCustomer(result: Result<Any>) {
        if (result is Result.Success) {
            result.data?.let { userModel ->
                marketingRepository.registerCustomer(
                    makeCustomer(userModel as UserModel)
                )
            }
        }
    }

    suspend fun sendMarketingEvent(result: Result<Any>, marketingEventType: MarketingEventType) {
        if (result is Result.Success) {
            result.data?.let { data ->
                when (marketingEventType) {
                    MarketingEventType.REGISTRATION -> {
                        marketingRepository.sendMarketingEvent(
                            makeRegistrationEvent(data as UserModel)
                        )
                    }
                    MarketingEventType.LOGIN -> {
                        marketingRepository.sendMarketingEvent(
                            makeLoginEvent(data as String)
                        )
                    }
                    MarketingEventType.PURCHASE -> {
                        marketingRepository.sendMarketingEvent(
                            makeRegistrationEvent(data as UserModel)
                        )
                    }
                }
            }
        }
    }

    suspend fun sendProductEvent(result: Result<ProductModel>, productEventType: ProductEventType) {
        if (result is Result.Success) {
            result.data?.let { productModel ->
                marketingRepository.sendMarketingProductEvent(
                    makeProductEvent(
                        productModel = productModel,
                        productEventType = productEventType
                    )
                )
            }
        }
    }

    suspend fun sendProductEventFromList(
        result: Result<List<ProductModel>>,
        productEventType: ProductEventType
    ) {
        if (result is Result.Success) {
            result.data?.let { list ->
                list.forEach { product ->
                    marketingRepository.sendMarketingProductEvent(
                        makeProductEvent(
                            productModel = product,
                            productEventType = productEventType
                        )
                    )
                }
            }
        }
    }

    suspend fun doOnPurchase(productResult: Result<List<ProductModel>>, userResult: Result<Any>) {
        sendProductEventFromList(
            result = productResult,
            ProductEventType.PURCHASE
        )
        sendMarketingEvent(
            result = userResult,
            MarketingEventType.PURCHASE
        )
    }

    private fun makeCustomer(userModel: UserModel): Customer {
        return outputCustomerMapper.toCustomer(
            userModel.uid,
            userModel.firstName,
            userModel.lastName,
            userModel.email
        )
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

    private fun makeLoginEvent(email: String): MarketingEvent {
        return MarketingEvent.Login(
            eventAttributesMapper.mapFromLoginInput(
                email = email
            )
        )
    }

    private fun makeProductEvent(
        productModel: ProductModel,
        productEventType: ProductEventType
    ): ProductEvent {
        return ProductEvent(
            productId = productModel.uid,
            eventType = productEventType
        )
    }
}
