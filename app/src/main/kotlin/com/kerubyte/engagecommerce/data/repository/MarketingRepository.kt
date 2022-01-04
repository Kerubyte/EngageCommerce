package com.kerubyte.engagecommerce.data.repository

import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.infrastructure.util.MarketingEvent
import com.user.sdk.events.ProductEventType

interface MarketingRepository {

    suspend fun registerUser(
        userId: String,
        firstName: String,
        lastName: String,
        email: String
    )

    suspend fun sendEvent(
        event: MarketingEvent.EventType,
        firstName: String? = null,
        lastName: String? = null,
        email: String? = null,
        newsletterSubscription: Boolean? = null,
        totalRevenue: String? = null
    )

    suspend fun sendProductEvent(
        productId: String,
        eventType: ProductEventType,
        product: Product
    )
}