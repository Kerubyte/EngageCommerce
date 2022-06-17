package com.kerubyte.engagecommerce.common.data


import com.kerubyte.engagecommerce.common.data.mapper.marketing.OutputCustomerMapper
import com.kerubyte.engagecommerce.common.data.mapper.marketing.OutputEventAttributesMapper
import com.kerubyte.engagecommerce.common.domain.DispatcherProvider
import com.kerubyte.engagecommerce.common.domain.MarketingRepository
import com.kerubyte.engagecommerce.common.domain.model.UserModel
import com.kerubyte.engagecommerce.common.util.Constants.ATTR_EMAIL
import com.kerubyte.engagecommerce.common.util.Constants.ATTR_FIRST_NAME
import com.kerubyte.engagecommerce.common.util.Constants.ATTR_LAST_NAME
import com.kerubyte.engagecommerce.common.util.MarketingEvent
import com.user.sdk.UserCom
import com.user.sdk.customer.CustomerUpdateCallback
import com.user.sdk.events.ProductEvent
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarketingRepositoryImpl
@Inject
constructor(
    private val userComInstance: UserCom,
    private val userComCallback: CustomerUpdateCallback,
    private val outputCustomerMapper: OutputCustomerMapper,
    private val eventAttributesMapper: OutputEventAttributesMapper,
    private val dispatcherProvider: DispatcherProvider
) : MarketingRepository {
    override suspend fun registerCustomer(user: UserModel) {
        val customer = outputCustomerMapper
            .toCustomer(
                user.uid,
                user.firstName,
                user.lastName,
                user.email
            )

        withContext(dispatcherProvider.io) {
            userComInstance.register(customer, userComCallback)
        }
    }

    override suspend fun sendMarketingEvent(marketingEvent: MarketingEvent) {
        withContext(dispatcherProvider.io) {

            when (marketingEvent) {
                is MarketingEvent.Registration -> {
                    userComInstance.sendEvent(
                        MarketingEvent.Registration(
                            eventAttributesMapper
                                .mapFromRegistrationInput(
                                    firstName = marketingEvent.eventAttributes[ATTR_FIRST_NAME] as String,
                                    lastName = marketingEvent.eventAttributes[ATTR_LAST_NAME] as String,
                                    email = marketingEvent.eventAttributes[ATTR_EMAIL] as String
                                )
                        )
                    )
                }
                is MarketingEvent.Login -> {
                    userComInstance.sendEvent(
                        MarketingEvent.Login(
                            eventAttributesMapper.mapFromLoginInput(
                                email = marketingEvent.eventAttributes[ATTR_EMAIL] as String
                            )
                        )
                    )
                }
            }
        }
    }

    override suspend fun sendMarketingProductEvent(productEvent: ProductEvent) {
        TODO("Not yet implemented")
    }
}