package com.kerubyte.engagecommerce.common.data

import com.kerubyte.engagecommerce.common.util.DatabaseMapper
import com.kerubyte.engagecommerce.common.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.common.domain.model.User

interface NullableDatabaseUserMapper<Entity, Model> : DatabaseMapper<Entity?, Model>


class NullableInputDatabaseUserMapper : DatabaseMapper<DatabaseUser?, User> {

    fun mapFromDatabase(entity: DatabaseUser?): User {
        return User(
            uid = entity?.uid.orEmpty(),
            firstName = entity?.firstName.orEmpty(),
            lastName = entity?.lastName.orEmpty(),
            email = entity?.email.orEmpty(),
            cart = entity?.cart.orEmpty(),
            address = entity?.address.orEmpty()
        )
    }
}