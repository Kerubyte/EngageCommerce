package com.kerubyte.engagecommerce.common.domain

import com.kerubyte.engagecommerce.common.domain.model.UserModel
import com.kerubyte.engagecommerce.common.util.MarketingEvent
import com.user.sdk.events.ProductEvent

interface MarketingRepository {

    suspend fun registerCustomer(user: UserModel)

    suspend fun sendMarketingEvent(marketingEvent: MarketingEvent)

    suspend fun sendMarketingProductEvent(productEvent: ProductEvent)
}