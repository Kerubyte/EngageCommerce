package com.kerubyte.engagecommerce.infrastructure.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kerubyte.engagecommerce.data.database.Authenticator
import com.kerubyte.engagecommerce.data.database.DatabaseInteractor
import com.kerubyte.engagecommerce.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.data.util.DispatcherProvider
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.infrastructure.Constants.COLLECTION_USERS
import com.kerubyte.engagecommerce.infrastructure.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.infrastructure.mapper.user.NullableOutputDatabaseUserMapper
import com.kerubyte.engagecommerce.infrastructure.util.Result
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
    private val auth: FirebaseAuth,
    private val databaseInteractor: DatabaseInteractor,
    private val authenticator: Authenticator,
    private val dispatcherProvider: DispatcherProvider,
    private val inputDatabaseUserMapper: NullableInputDatabaseUserMapper,
    private val outputDatabaseUserMapper: NullableOutputDatabaseUserMapper,
    private val firestore: FirebaseFirestore
) : UserRepository {

    private val currentUserUid = authenticator.getCurrentUserUid()

    override fun getCurrentUser(): DocumentReference? {

        return auth.currentUser?.let {
            firestore.collection(COLLECTION_USERS)
                .document(auth.currentUser!!.uid)
        }
    }

    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<Nothing> = withContext(dispatcherProvider.io) {

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

                Result.Success(null)

            } ?: Result.Error.AuthenticationError(null)
        } catch (exc: Exception) {
            Result.Error.NetworkError(exc.message)
        }
    }

    override suspend fun loginUser(email: String, password: String): Result<Nothing> =

        withContext(dispatcherProvider.io) {

            try {
                authenticator
                    .loginUserWithEmailAndPassword(
                        email,
                        password
                    )
                    .await()

                Result.Success(null)
            } catch (exc: Exception) {
                Result.Error.NetworkError(exc.message)
            }
        }

    override suspend fun getUserData(): Result<User> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->

                try {
                    val documentSnapshot = databaseInteractor
                        .getSingleDocument(COLLECTION_USERS, uid)
                        .await()
                    val response = documentSnapshot.toObject(DatabaseUser::class.java)
                    val result = inputDatabaseUserMapper.mapFromDatabase(response)

                    Result.Success(result)
                } catch (exc: Exception) {
                    Result.Error.NetworkError(exc.message)
                }
            } ?: Result.Error.AuthenticationError(null)
        }

    override suspend fun addToCart(productUid: String): Result<Nothing> =

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

                    Result.Success(null)
                } catch (exc: Exception) {
                    Result.Error.NetworkError(exc.message)
                }
            } ?: Result.Error.AuthenticationError(null)
        }

    override suspend fun removeFromCart(productUid: String): Result<Nothing> =

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

                    Result.Success(null)
                } catch (exc: Exception) {
                    Result.Error.NetworkError(exc.message)
                }
            } ?: Result.Error.AuthenticationError(null)
        }

    override suspend fun clearUserCart(): Result<Nothing> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->

                try {

                    databaseInteractor
                        .deleteFieldInDocument(
                            COLLECTION_USERS,
                            uid
                        )
                        .await()

                    Result.Success(null)
                } catch (exc: Exception) {
                    Result.Error.NetworkError(exc.message)
                }
            } ?: Result.Error.AuthenticationError(null)
        }

    override suspend fun updateAddress(userAddress: Map<String, String>): Result<Nothing> =

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

                    Result.Success(null)
                } catch (exc: Exception) {
                    Result.Error.NetworkError(exc.message)
                }
            } ?: Result.Error.AuthenticationError(null)
        }
}