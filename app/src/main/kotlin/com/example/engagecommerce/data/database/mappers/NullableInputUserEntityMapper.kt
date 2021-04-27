package com.example.engagecommerce.data.database.mappers

import com.example.engagecommerce.application.util.UserEntityMapper
import com.example.engagecommerce.data.entity.UserEntity
import com.example.engagecommerce.domain.model.User
import javax.inject.Inject

interface NullableUserEntityMapper<Entity, Model> : UserEntityMapper<Entity?, Model>

class NullableInputUserEntityMapper
@Inject
constructor() : NullableUserEntityMapper<UserEntity?, User> {

    fun mapFromEntity(entity: UserEntity?): User {
        return User(
            uid = entity?.uid.orEmpty(),
            firstName = entity?.firstName.orEmpty(),
            lastName = entity?.lastName.orEmpty(),
            email = entity?.email.orEmpty(),
            cart = entity?.cart ?: listOf("hejaaa")
        )
    }
}