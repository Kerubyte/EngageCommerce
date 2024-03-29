package com.kerubyte.engagecommerce.feature.auth.data

import com.kerubyte.engagecommerce.common.data.mapper.user.NullableOutputDatabaseUserMapper
import com.kerubyte.engagecommerce.common.domain.DatabaseInteractor
import com.kerubyte.engagecommerce.common.domain.DispatcherProvider
import com.kerubyte.engagecommerce.common.domain.model.UserModel
import com.kerubyte.engagecommerce.common.util.Constants
import com.kerubyte.engagecommerce.common.util.Result
import com.kerubyte.engagecommerce.feature.auth.domain.AuthRepository
import com.kerubyte.engagecommerce.feature.auth.domain.Authenticator
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl
@Inject
constructor(
    private val authenticator: Authenticator,
    private val dispatcherProvider: DispatcherProvider,
    private val databaseInteractor: DatabaseInteractor,
    private val outputDatabaseUserMapper: NullableOutputDatabaseUserMapper
) : AuthRepository {

    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<Any> = withContext(dispatcherProvider.io) {
        authenticator
            .createUserWithEmailAndPassword(
                email = email,
                password = password
            )
            .await()

        val currentUid = authenticator.getCurrentUserUid()

        currentUid?.let { uid ->
            val user = UserModel(
                uid,
                firstName,
                lastName,
                email,
                listOf(),
                emptyMap()
            )
            val databaseUser = outputDatabaseUserMapper.mapToEntity(user)

            databaseInteractor
                .setDocumentInCollection(
                    Constants.COLLECTION_USERS,
                    uid,
                    databaseUser
                )
                .await()

            Result.Success(data = user)
        } ?: Result.Error.AuthenticationError(null)
    }

    override suspend fun loginUser(email: String, password: String): Result<Any> =

        withContext(dispatcherProvider.io) {
            authenticator
                .loginUserWithEmailAndPassword(
                    email = email,
                    password = password
                )
                .await()

            Result.Success(data = email)
        }
}