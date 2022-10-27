package com.kerubyte.engagecommerce.common.data


import android.util.Log
import com.kerubyte.engagecommerce.common.domain.DispatcherProvider
import com.kerubyte.engagecommerce.common.domain.MarketingRepository
import com.kerubyte.engagecommerce.common.util.MarketingEvent
import com.user.sdk.UserCom
import com.user.sdk.customer.Customer
import com.user.sdk.customer.CustomerUpdateCallback
import com.user.sdk.customer.RegisterResponse
import com.user.sdk.events.ProductEvent
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarketingRepositoryImpl
@Inject
constructor(
    private val userComInstance: UserCom,
    private val userComCallback: CustomerUpdateCallback,
    private val dispatcherProvider: DispatcherProvider
) : MarketingRepository {
    override suspend fun registerCustomer(customer: Customer) {
        withContext(dispatcherProvider.io) {

                    UserCom.getInstance().register(customer, object : CustomerUpdateCallback {
                    override fun onSuccess(response: RegisterResponse) {
                        Log.d("REGGEREPO", "onSuccess: ${response.key}")
                        // user is registered and now you can send events via SDK
                    }

                    override fun onFailure(throwable: Throwable) {
                        Log.e("REGGEREPO", "onFailure: ", throwable)
                        // try again - something went wrong
                    }
                })
        }
    }

    override suspend fun sendMarketingEvent(marketingEvent: MarketingEvent) {
        withContext(dispatcherProvider.io) {
            when (marketingEvent) {
                is MarketingEvent.Registration -> {
                    userComInstance.sendEvent(marketingEvent)
                }
                is MarketingEvent.Login -> {
                    userComInstance.sendEvent(marketingEvent)
                }
                is MarketingEvent.PurchaseSummary -> {
                    userComInstance.sendEvent(marketingEvent)
                }
            }
        }
    }

    override suspend fun sendMarketingProductEvent(productEvent: ProductEvent) {
        userComInstance.sendProductEvent(
            productEvent.productId,
            productEvent.eventType,
            null
        )
    }
}

