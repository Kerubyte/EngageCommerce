package com.kerubyte.engagecommerce.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.kerubyte.engagecommerce.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.data.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.data.mapper.user.NullableOutputDatabaseUserMapper
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.domain.repo.UserRepository
import com.kerubyte.engagecommerce.infrastructure.Constants.COLLECTION_USERS
import com.kerubyte.engagecommerce.infrastructure.state.CartState
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.infrastructure.util.Status
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val inputDatabaseUserMapper: NullableInputDatabaseUserMapper,
    private val outputDatabaseUserMapper: NullableOutputDatabaseUserMapper
) : UserRepository {

    private val currentUserUid = firebaseAuth.currentUser?.uid

    override suspend fun createAccount(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Resource<Status> {

        return try {

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .await()

            val user = User(
                firebaseAuth.currentUser!!.uid,
                firstName,
                lastName,
                email,
                listOf()
            )
            val databaseUser = outputDatabaseUserMapper.mapToEntity(user)

            firestore.collection(COLLECTION_USERS)
                .document(user.uid)
                .set(databaseUser)
                .await()
            Resource(Status.SUCCESS, null, null)

        } catch (exc: Exception) {
            Resource(Status.ERROR, null, exc.message)
        }
    }

    override suspend fun loginUser(email: String, password: String): Resource<Status> {

        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .await()
            Resource(Status.SUCCESS, null, null)

        } catch (exc: Exception) {
            Resource(Status.ERROR, null, exc.message)
        }
    }

    override suspend fun getUserData(): Resource<User> {

        val currentUserUid = firebaseAuth.currentUser?.uid

        currentUserUid?.let { uid ->

            return try {
                val querySnapshot = firestore.collection(COLLECTION_USERS)
                    .document(uid)
                    .get()
                    .await()
                val response = querySnapshot.toObject(DatabaseUser::class.java)
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
                firestore.collection(COLLECTION_USERS)
                    .document(uid)
                    .update("cart", FieldValue.arrayUnion(productUid))
                    .await()
                Resource(Status.SUCCESS, null, null)
            } catch (exc: Exception) {
                Resource(Status.ERROR, null, exc.message)
            }
        }
        return Resource(Status.ERROR, null, "User not logged in")
    }

    override suspend fun getUserCart(): CartState {

        currentUserUid?.let { uid ->

            return try {
                val documentSnapshot = firestore.collection(COLLECTION_USERS)
                    .document(uid)
                    .get()
                    .await()
                val result = documentSnapshot.toObject(DatabaseUser::class.java)
                val user = inputDatabaseUserMapper.mapFromDatabase(result)
                if (user.cart.isNullOrEmpty()) {
                    CartState.Empty
                } else {
                    CartState.NotEmpty(user.cart)
                }
            } catch (exc: Exception) {
                CartState.Error
            }

        }
        return CartState.Error
    }
}