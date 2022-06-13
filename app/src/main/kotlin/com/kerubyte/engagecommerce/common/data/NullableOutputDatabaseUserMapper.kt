package com.kerubyte.engagecommerce.common.data

import com.kerubyte.engagecommerce.common.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.common.util.DatabaseMapper
import com.kerubyte.engagecommerce.common.domain.model.User

class NullableOutputDatabaseUserMapper : DatabaseMapper<DatabaseUser?, User> {

    fun mapToEntity(model: User): DatabaseUser {
        return DatabaseUser(
            uid = model.uid,
            firstName = model.firstName,
            lastName = model.lastName,
            email = model.email,
            cart = model.cart,
            address = model.address
        )
    }
}