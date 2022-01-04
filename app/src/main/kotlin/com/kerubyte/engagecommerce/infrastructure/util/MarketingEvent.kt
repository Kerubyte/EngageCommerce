package com.kerubyte.engagecommerce.infrastructure.util

import com.user.sdk.events.Event
import com.user.sdk.events.UserComEvent

sealed class MarketingEvent(
    val eventAttributes: HashMap<String, Any>
) : UserComEvent {

    @Event(name = "Login")
    class Login(eventAttributes: HashMap<String, Any>): MarketingEvent(eventAttributes) {
        override fun toFlat(): MutableMap<String, Any> {
            return eventAttributes
        }
    }

    @Event(name = "Register")
    class Register(eventAttributes: HashMap<String, Any>): MarketingEvent(eventAttributes) {
        override fun toFlat(): MutableMap<String, Any> {
            return eventAttributes
        }
    }

    @Event(name = "Purchase Summary")
    class PurchaseSummary(eventAttributes: HashMap<String, Any>): MarketingEvent(eventAttributes) {
        override fun toFlat(): MutableMap<String, Any> {
            return eventAttributes
        }
    }

    @Event(name = "Newsletter Subscription")
    class NewsletterSubscription(eventAttributes: HashMap<String, Any>): MarketingEvent(eventAttributes) {
        override fun toFlat(): MutableMap<String, Any> {
            return eventAttributes
        }
    }

    enum class EventType {
        REGISTER,
        LOGIN,
        PURCHASE_SUMMARY,
        NEWSLETTER_SUBSCRIPTION
    }
}