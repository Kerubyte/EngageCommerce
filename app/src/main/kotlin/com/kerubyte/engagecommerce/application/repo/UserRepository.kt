package com.kerubyte.engagecommerce.application.repo

import com.kerubyte.engagecommerce.application.utils.Resource
import com.kerubyte.engagecommerce.domain.model.local.User

interface UserRepository {

    suspend fun getUserData(): Resource<User>
}