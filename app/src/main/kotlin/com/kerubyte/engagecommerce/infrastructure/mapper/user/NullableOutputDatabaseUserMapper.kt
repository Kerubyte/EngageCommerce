package com.kerubyte.engagecommerce.infrastructure.mapper.user

import com.kerubyte.engagecommerce.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.data.mapper.DatabaseMapper
import com.kerubyte.engagecommerce.domain.model.User

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