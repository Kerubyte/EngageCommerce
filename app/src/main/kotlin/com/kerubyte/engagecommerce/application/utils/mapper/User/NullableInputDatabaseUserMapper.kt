package com.kerubyte.engagecommerce.application.utils.mapper.User

import com.kerubyte.engagecommerce.application.utils.mapper.DatabaseMapper
import com.kerubyte.engagecommerce.domain.model.database.DatabaseUser
import com.kerubyte.engagecommerce.domain.model.local.User

interface NullableDatabaseUserMapper<Entity, Model> : DatabaseMapper<Entity?, Model>


class NullableInputDatabaseUserMapper : NullableDatabaseUserMapper<DatabaseUser, User> {

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