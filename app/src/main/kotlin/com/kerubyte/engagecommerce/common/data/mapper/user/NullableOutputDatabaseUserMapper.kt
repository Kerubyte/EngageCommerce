package com.kerubyte.engagecommerce.common.data.mapper.user

import com.kerubyte.engagecommerce.common.data.entity.UserEntity
import com.kerubyte.engagecommerce.common.util.DatabaseMapper
import com.kerubyte.engagecommerce.common.domain.model.UserModel

class NullableOutputDatabaseUserMapper : DatabaseMapper<UserEntity?, UserModel> {

    fun mapToEntity(model: UserModel): UserEntity {
        return UserEntity(
            uid = model.uid,
            firstName = model.firstName,
            lastName = model.lastName,
            email = model.email,
            cart = model.cart,
            address = model.address
        )
    }
}