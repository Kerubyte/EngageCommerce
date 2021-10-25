package com.kerubyte.engagecommerce.infrastructure.remote

import com.kerubyte.engagecommerce.data.database.Authenticator
import com.kerubyte.engagecommerce.data.database.DatabaseInteractor
import com.kerubyte.engagecommerce.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.data.util.DispatcherProvider
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.Constants.COLLECTION_USERS
import com.kerubyte.engagecommerce.infrastructure.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.infrastructure.mapper.user.NullableOutputDatabaseUserMapper
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
    private val databaseInteractor: DatabaseInteractor,
    private val authenticator: Authenticator,
    private val dispatcherProvider: DispatcherProvider,
    private val inputDatabaseUserMapper: NullableInputDatabaseUserMapper,
    private val outputDatabaseUserMapper: NullableOutputDatabaseUserMapper
) : UserRepository {

    private val currentUserUid = authenticator.getCurrentUserUid()

    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Resource<Nothing> = withContext(dispatcherProvider.io) {

        try {

            authenticator
                .createUserWithEmailAndPassword(email, password)
                .await()

            val currentUid = authenticator.getCurrentUserUid()

            currentUid?.let { uid ->

                val user = User(
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
                        COLLECTION_USERS,
                        uid,
                        databaseUser
                    )
                    .await()

                Resource.Success(null)

            } ?: Resource.Error.AuthenticationError(null)
        } catch (exc: Exception) {
            Resource.Error.NetworkError(exc.message)
        }
    }

    override suspend fun loginUser(email: String, password: String): Resource<Nothing> =

        withContext(dispatcherProvider.io) {

            try {
                authenticator
                    .loginUserWithEmailAndPassword(
                        email,
                        password
                    )
                    .await()

                Resource.Success(null)
            } catch (exc: Exception) {
                Resource.Error.NetworkError(exc.message)
            }
        }

    override suspend fun getUserData(): Resource<User> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->

                try {
                    val documentSnapshot = databaseInteractor
                        .getSingleDocument(COLLECTION_USERS, uid)
                        .await()
                    val response = documentSnapshot.toObject(DatabaseUser::class.java)
                    val result = inputDatabaseUserMapper.mapFromDatabase(response)

                    Resource.Success(result)
                } catch (exc: Exception) {
                    Resource.Error.NetworkError(exc.message)
                }
            } ?: Resource.Error.AuthenticationError(null)
        }

    override suspend fun addToCart(productUid: String): Resource<Nothing> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->

                try {
                    databaseInteractor
                        .addToFieldInDocument(
                            COLLECTION_USERS,
                            uid,
                            productUid
                        )
                        .await()

                    Resource.Success(null)
                } catch (exc: Exception) {
                    Resource.Error.NetworkError(exc.message)
                }
            } ?: Resource.Error.AuthenticationError(null)
        }

    override suspend fun removeFromCart(productUid: String): Resource<Nothing> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->

                try {

                    databaseInteractor
                        .removeFromFieldInDocument(
                            COLLECTION_USERS,
                            uid,
                            productUid
                        )
                        .await()

                    Resource.Success(null)
                } catch (exc: Exception) {
                    Resource.Error.NetworkError(exc.message)
                }
            } ?: Resource.Error.AuthenticationError(null)
        }

    override suspend fun clearUserCart(): Resource<Nothing> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->

                try {

                    databaseInteractor
                        .deleteFieldInDocument(
                            COLLECTION_USERS,
                            uid
                        )
                        .await()

                    Resource.Success(null)
                } catch (exc: Exception) {
                    Resource.Error.NetworkError(exc.message)
                }
            } ?: Resource.Error.AuthenticationError(null)
        }

    override suspend fun updateAddress(userAddress: Map<String, String>): Resource<Nothing> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->

                try {

                    databaseInteractor
                        .updateDocument(
                            COLLECTION_USERS,
                            uid,
                            userAddress
                        )
                        .await()

                    Resource.Success(null)
                } catch (exc: Exception) {
                    Resource.Error.NetworkError(exc.message)
                }
            } ?: Resource.Error.AuthenticationError(null)
        }
}