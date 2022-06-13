package com.kerubyte.engagecommerce.common.data

import com.kerubyte.engagecommerce.common.util.DatabaseMapper
import com.kerubyte.engagecommerce.common.data.entity.UserEntity
import com.kerubyte.engagecommerce.common.domain.model.UserModel

interface NullableDatabaseUserMapper<Entity, Model> : DatabaseMapper<Entity?, Model>


class NullableInputDatabaseUserMapper : DatabaseMapper<UserEntity?, UserModel> {

    fun mapFromDatabase(entity: UserEntity?): UserModel {
        return UserModel(
            uid = entity?.uid.orEmpty(),
            firstName = entity?.firstName.orEmpty(),
            lastName = entity?.lastName.orEmpty(),
            email = entity?.email.orEmpty(),
            cart = entity?.cart.orEmpty(),
            address = entity?.address.orEmpty()
        )
    }
}