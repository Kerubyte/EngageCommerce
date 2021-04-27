package com.example.engagecommerce.data.database.mappers

import com.example.engagecommerce.data.entity.UserEntity
import com.example.engagecommerce.domain.model.User
import javax.inject.Inject

class NullableOutputUserEntityMapper
@Inject
constructor() : NullableUserEntityMapper<UserEntity, User> {

    fun mapToEntity(model: User): UserEntity {
        return UserEntity(
            uid = model.uid,
            firstName = model.firstName,
            lastName = model.lastName,
            email = model.email,
            cart = model.cart
        )
    }
}