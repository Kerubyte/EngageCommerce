package com.kerubyte.engagecommerce.infrastructure.mapper.marketing

import com.kerubyte.engagecommerce.data.mapper.DatabaseMapper

class OutputEventAttributesMapper : DatabaseMapper<Any, MutableMap<String, Any>> {

    fun mapFromRegisterInputs(
        firstName: String,
        lastName: String,
        email: String
    ): HashMap<String, Any> =

        hashMapOf(
            "First Name" to firstName,
            "Last Name" to lastName,
            "Email" to email
        )

    fun mapFromLoginInputs(
        email: String
    ): HashMap<String, Any> =

        hashMapOf(
            "Email" to email
        )

    fun mapFromNewsletterInputs(
        email: String,
        newsletterSubscription: Boolean
    ): HashMap<String, Any> =

        hashMapOf(
            "Email" to email,
            "Newsletter Subscription" to newsletterSubscription
        )

    fun mapFromPurchase(
        totalRevenue: String
    ): HashMap<String, Any> =

        hashMapOf(
            "Total Revenue" to totalRevenue
        )
}