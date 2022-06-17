package com.kerubyte.engagecommerce.common.data.mapper.marketing

import com.kerubyte.engagecommerce.common.domain.model.UserModel
import com.kerubyte.engagecommerce.common.util.DatabaseMapper
import com.user.sdk.customer.Customer

class OutputCustomerMapper : DatabaseMapper<UserModel, Customer> {

    fun toCustomer(
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