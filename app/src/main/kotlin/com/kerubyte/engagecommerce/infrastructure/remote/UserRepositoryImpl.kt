package com.kerubyte.engagecommerce.infrastructure.remote

import com.kerubyte.engagecommerce.data.database.Authenticator
import com.kerubyte.engagecommerce.data.database.DatabaseInteractor
import com.kerubyte.engagecommerce.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.Constants.COLLECTION_USERS
import com.kerubyte.engagecommerce.infrastructure.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.infrastructure.mapper.user.NullableOutputDatabaseUserMapper
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.infrastructure.util.Status
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
    private val databaseInteractor: DatabaseInteractor,
    private val authenticator: Authenticator,
    private val inputDatabaseUserMapper: NullableInputDatabaseUserMapper,
    private val outputDatabaseUserMapper: NullableOutputDatabaseUserMapper
) : UserRepository {

    private val currentUserUid = authenticator.getCurrentUserUid()

    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Resource<Status> {

        return try {

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

                Resource(Status.SUCCESS, null, null)
            } ?: Resource(Status.ERROR, null, "Unknown error")
        } catch (exc: Exception) {
            Resource(Status.ERROR, null, exc.message)
        }
    }

    override suspend fun loginUser(email: String, password: String): Resource<Status> {

        return try {
            authenticator
                .loginUserWithEmailAndPassword(
                    email,
                    password
                )
                .await()

            Resource(Status.SUCCESS, null, null)
        } catch (exc: Exception) {
            Resource(Status.ERROR, null, exc.message)
        }
    }

    override suspend fun getUserData(): Resource<User> {

        currentUserUid?.let { uid ->

            return try {
                val documentSnapshot = databaseInteractor
                    .getSingleDocument(COLLECTION_USERS, uid)
                    .await()
                val response = documentSnapshot.toObject(DatabaseUser::class.java)
                val result = inputDatabaseUserMapper.mapFromDatabase(response)

                Resource(Status.SUCCESS, result, null)
            } catch (exc: Exception) {
                Resource(Status.ERROR, null, exc.message)
            }
        }
        return Resource(Status.ERROR, null, "User not logged in")
    }

    override suspend fun addToCart(productUid: String): Resource<Status> {

        currentUserUid?.let { uid ->

            return try {
                databaseInteractor
                    .addToFieldInDocument(
                        COLLECTION_USERS,
                        uid,
                        productUid
                    )
                    .await()

                Resource(Status.SUCCESS, null, null)
            } catch (exc: Exception) {
                Resource(Status.ERROR, null, exc.message)
            }
        }
        return Resource(Status.ERROR, null, "User not logged in")
    }

    override suspend fun removeFromCart(productUid: String): Resource<Status> {

        currentUserUid?.let { uid ->

            return try {

                databaseInteractor
                    .removeFromFieldInDocument(
                        COLLECTION_USERS,
                        uid,
                        productUid
                    )
                    .await()

                Resource(Status.SUCCESS, null, null)
            } catch (exc: Exception) {
                Resource(Status.ERROR, null, exc.message)
            }
        }
        return Resource(Status.ERROR, null, "User not logged in")
    }

    override suspend fun clearUserCart(): Resource<Status> {

        currentUserUid?.let { uid ->

            return try {

                databaseInteractor
                    .deleteFieldInDocument(
                        COLLECTION_USERS,
                        uid
                    )
                    .await()

                Resource(Status.SUCCESS, null, null)
            } catch (exc: Exception) {
                Resource(Status.ERROR, null, exc.message)
            }
        }
        return Resource(Status.ERROR, null, "User not logged in")
    }

    override suspend fun updateAddress(userAddress: Map<String, String>): Resource<Status> {

        currentUserUid?.let { uid ->

            return try {

                databaseInteractor
                    .updateDocument(
                        COLLECTION_USERS,
                        uid,
                        userAddress
                    )
                    .await()

                Resource(Status.SUCCESS, null, null)
            } catch (exc: Exception) {
                Resource(Status.ERROR, null, exc.message)
            }
        }
        return Resource(Status.ERROR, null, "User not logged in")
    }
}