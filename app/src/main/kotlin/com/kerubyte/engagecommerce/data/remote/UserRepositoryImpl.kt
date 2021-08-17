package com.kerubyte.engagecommerce.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kerubyte.engagecommerce.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.data.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.data.mapper.user.NullableOutputDatabaseUserMapper
import com.kerubyte.engagecommerce.domain.model.User
import com.kerubyte.engagecommerce.domain.repo.UserRepository
import com.kerubyte.engagecommerce.infrastructure.Constants.COLLECTION_USERS
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
}