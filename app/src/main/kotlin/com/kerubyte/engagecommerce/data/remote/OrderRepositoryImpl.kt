package com.kerubyte.engagecommerce.data.remote

import android.icu.util.Calendar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kerubyte.engagecommerce.domain.repo.OrderRepository
import com.kerubyte.engagecommerce.infrastructure.Constants.COLLECTION_ORDERS
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.infrastructure.util.Status
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepositoryImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val calendar: Calendar
) : OrderRepository {

    override suspend fun createOrder(userOrder: Map<String, Any>): Resource<Status> {

        val currentUserUid = firebaseAuth.currentUser?.uid
        val timeNow = calendar.time.toString()

        currentUserUid?.let { uid ->

            return try {

                firestore.collection(COLLECTION_ORDERS)
                    .document(uid)
                    .collection(COLLECTION_ORDERS)
                    .document(timeNow)
                    .set(userOrder)
                    .await()
                Resource(Status.SUCCESS, null, null)
            } catch (exc: Exception) {
                Resource(Status.ERROR, null, exc.message)
            }
        }
        return Resource(Status.ERROR, null, "User not logged in")
    }
}