package com.kerubyte.engagecommerce.common.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kerubyte.engagecommerce.feature.auth.domain.Authenticator
import com.kerubyte.engagecommerce.common.domain.DatabaseInteractor
import com.kerubyte.engagecommerce.common.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.common.domain.UserRepository
import com.kerubyte.engagecommerce.common.util.DispatcherProvider
import com.kerubyte.engagecommerce.common.domain.model.User
import com.kerubyte.engagecommerce.common.util.Constants.COLLECTION_USERS
import com.kerubyte.engagecommerce.common.util.Result
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

    override suspend fun getUserData(): Result<User> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->
                val documentSnapshot = databaseInteractor
                    .getSingleDocument(COLLECTION_USERS, uid)
                    .await()
                val response = documentSnapshot.toObject(DatabaseUser::class.java)
                val result = inputDatabaseUserMapper.mapFromDatabase(response)

                Result.Success(result)
            } ?: Result.Error.AuthenticationError(null)
        }

    override suspend fun addToCart(productUid: String): Result<Nothing> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->
                databaseInteractor
                    .addToFieldInDocument(
                        COLLECTION_USERS,
                        uid,
                        productUid
                    )
                    .await()

                Result.Success(null)
            } ?: Result.Error.AuthenticationError(null)
        }

    override suspend fun removeFromCart(productUid: String): Result<Nothing> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->
                databaseInteractor
                    .removeFromFieldInDocument(
                        COLLECTION_USERS,
                        uid,
                        productUid
                    )
                    .await()

                Result.Success(null)
            } ?: Result.Error.AuthenticationError(null)
        }

    override suspend fun clearUserCart(): Result<Nothing> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->
                databaseInteractor
                    .deleteFieldInDocument(
                        COLLECTION_USERS,
                        uid
                    )
                    .await()

                Result.Success(null)
            } ?: Result.Error.AuthenticationError(null)
        }

    override suspend fun updateAddress(userAddress: Map<String, String>): Result<Nothing> =

        withContext(dispatcherProvider.io) {

            currentUserUid?.let { uid ->
                databaseInteractor
                    .updateDocument(
                        COLLECTION_USERS,
                        uid,
                        userAddress
                    )
                    .await()

                Result.Success(null)
            } ?: Result.Error.AuthenticationError(null)
        }
}