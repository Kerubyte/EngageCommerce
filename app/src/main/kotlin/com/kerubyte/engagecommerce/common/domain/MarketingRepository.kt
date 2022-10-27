package com.kerubyte.engagecommerce.common.domain

import com.kerubyte.engagecommerce.common.util.MarketingEvent
import com.user.sdk.customer.Customer
import com.user.sdk.events.ProductEvent

interface MarketingRepository {

    suspend fun registerCustomer(customer: Customer)

    suspend fun sendMarketingEvent(marketingEvent: MarketingEvent)

    suspend fun sendMarketingProductEvent(productEvent: ProductEvent)
}