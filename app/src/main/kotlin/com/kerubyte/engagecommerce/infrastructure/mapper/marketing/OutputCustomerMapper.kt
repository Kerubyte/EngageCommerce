package com.kerubyte.engagecommerce.infrastructure.mapper.marketing

import com.kerubyte.engagecommerce.data.mapper.DatabaseMapper
import com.user.sdk.customer.Customer

class OutputCustomerMapper : DatabaseMapper<String, Customer> {

    fun mapToCustomer(
        userId: String,
        firstName: String,
        lastName: String,
        email: String
    ): Customer =
        Customer()
            .id(userId)
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
}