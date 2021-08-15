package com.kerubyte.engagecommerce.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kerubyte.engagecommerce.application.repo.UserRepository
import com.kerubyte.engagecommerce.application.utils.Constants.COLLECTION_USERS
import com.kerubyte.engagecommerce.application.utils.Resource
import com.kerubyte.engagecommerce.application.utils.Status
import com.kerubyte.engagecommerce.application.utils.mapper.User.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.domain.model.database.DatabaseUser
import com.kerubyte.engagecommerce.domain.model.local.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val databaseUserMapper: NullableInputDatabaseUserMapper
) : UserRepository {

    override suspend fun getUserData(): Resource<User> {

        val currentUserUid = firebaseAuth.currentUser?.uid

        currentUserUid?.let { uid ->

            return try {
                val querySnapshot = firestore.collection(COLLECTION_USERS)
                    .document(uid)
                    .get()
                    .await()
                val response = querySnapshot.toObject(DatabaseUser::class.java)
                val result = databaseUserMapper.mapFromDatabase(response)
                Resource(Status.SUCCESS, result, null)
            } catch (exc: Exception) {
                Resource(Status.ERROR, null, exc.message)
            }
        }
        return Resource(Status.ERROR, null, "User not logged in")
    }
}