package com.kerubyte.engagecommerce.data.mapper.user

import com.kerubyte.engagecommerce.data.mapper.DatabaseMapper
import com.kerubyte.engagecommerce.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.domain.model.User

interface NullableDatabaseUserMapper<Entity, Model> : DatabaseMapper<Entity?, Model>


class NullableInputDatabaseUserMapper : DatabaseMapper<DatabaseUser?, User> {

    fun mapFromDatabase(entity: DatabaseUser?): User {
        return User(
            uid = entity?.uid.orEmpty(),
            firstName = entity?.firstName.orEmpty(),
            lastName = entity?.lastName.orEmpty(),
            email = entity?.email.orEmpty(),
            cart = entity?.cart.orEmpty()
        )
    }
}