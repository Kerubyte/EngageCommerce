package com.kerubyte.engagecommerce.domain.repo

import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.domain.model.User

interface UserRepository {

    suspend fun getUserData(): Resource<User>
}