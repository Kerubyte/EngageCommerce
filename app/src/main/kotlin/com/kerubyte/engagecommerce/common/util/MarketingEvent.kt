package com.kerubyte.engagecommerce.common.util

import android.annotation.SuppressLint
import com.user.sdk.events.Event
import com.user.sdk.events.UserComEvent

sealed class MarketingEvent(
    val eventAttributes: HashMap<String, Any>
) : UserComEvent {

    @Event(name = "Registration")
    class Registration(eventAttributes: HashMap<String, Any>): MarketingEvent(eventAttributes) {
        @SuppressLint("RestrictedApi")
        override fun toFlat(): MutableMap<String, Any> {
            return eventAttributes
        }
    }

    @Event(name = "Login")
    class Login(eventAttributes: HashMap<String, Any>): MarketingEvent(eventAttributes) {
        @SuppressLint("RestrictedApi")
        override fun toFlat(): MutableMap<String, Any> {
            return eventAttributes
        }
    }
}
