package com.kerubyte.engagecommerce.infrastructure.remote

import android.util.Log
import com.kerubyte.engagecommerce.data.repository.MarketingRepository
import com.kerubyte.engagecommerce.data.util.DispatcherProvider
import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.infrastructure.mapper.marketing.OutputCustomerMapper
import com.kerubyte.engagecommerce.infrastructure.mapper.marketing.OutputEventAttributesMapper
import com.kerubyte.engagecommerce.infrastructure.mapper.marketing.OutputProductAttributesMapper
import com.kerubyte.engagecommerce.infrastructure.util.MarketingEvent
import com.user.sdk.UserCom
import com.user.sdk.customer.CustomerUpdateCallback
import com.user.sdk.events.ProductEventType
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarketingRepositoryImpl
@Inject
constructor(
    private val userCom: UserCom,
    private val productAttributesMapper: OutputProductAttributesMapper,
    private val eventAttributesMapper: OutputEventAttributesMapper,
    private val outputCustomerMapper: OutputCustomerMapper,
    private val customerUpdateCallback: CustomerUpdateCallback,
    private val dispatcherProvider: DispatcherProvider
) : MarketingRepository {

    override suspend fun registerUser(
        userId: String,
        firstName: String,
        lastName: String,
        email: String
    ) {
        withContext(dispatcherProvider.io) {

            val customer = outputCustomerMapper
                .mapToCustomer(userId, firstName, lastName, email)

            try {
                userCom.register(customer, customerUpdateCallback)
            } catch (exc: Exception) {
                exc.message?.let { Log.d("registerNewUser", it) }
            }
        }
    }

    override suspend fun sendEvent(
        event: MarketingEvent.EventType,
        firstName: String?,
        lastName: String?,
        email: String?,
        newsletterSubscription: Boolean?,
        totalRevenue: String?
    ) {

        withContext(dispatcherProvider.io) {
            when (event) {

                MarketingEvent.EventType.REGISTER -> {
                    val eventAttributes = eventAttributesMapper
                        .mapFromRegisterInputs(firstName!!, lastName!!, email!!)
                    userCom.sendEvent(MarketingEvent.Register(eventAttributes))
                }

                MarketingEvent.EventType.LOGIN -> {
                    val eventAttributes = eventAttributesMapper
                        .mapFromLoginInputs(email!!)
                    userCom.sendEvent(MarketingEvent.Login(eventAttributes))
                }

                MarketingEvent.EventType.PURCHASE_SUMMARY -> {
                    val eventAttributes = eventAttributesMapper
                        .mapFromPurchase(totalRevenue!!)
                    Log.d("debunger", "$totalRevenue and $eventAttributes")
                    userCom.sendEvent(MarketingEvent.PurchaseSummary(eventAttributes))
                }

                MarketingEvent.EventType.NEWSLETTER_SUBSCRIPTION -> {
                    val eventAttributes = eventAttributesMapper
                        .mapFromNewsletterInputs(email!!, newsletterSubscription!!)
                    userCom.sendEvent(MarketingEvent.NewsletterSubscription(eventAttributes))
                }
            }
        }
    }

    override suspend fun sendProductEvent(
        productId: String,
        eventType: ProductEventType,
        product: Product
    ) {
        withContext(dispatcherProvider.io) {

            val productAttributes = productAttributesMapper.mapFromProduct(product)

            try {
                userCom.sendProductEvent(
                    productId,
                    eventType,
                    productAttributes
                )
            } catch (exc: Exception) {
                exc.message?.let { Log.d("sendEventProduct", it) }
            }
        }
    }
}