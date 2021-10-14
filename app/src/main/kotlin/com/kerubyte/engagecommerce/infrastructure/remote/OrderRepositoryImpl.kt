package com.kerubyte.engagecommerce.infrastructure.remote

import android.icu.util.Calendar
import com.kerubyte.engagecommerce.data.database.Authenticator
import com.kerubyte.engagecommerce.data.database.DatabaseInteractor
import com.kerubyte.engagecommerce.data.repository.OrderRepository
import com.kerubyte.engagecommerce.infrastructure.Constants.COLLECTION_ORDERS
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.infrastructure.util.Status
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OrderRepositoryImpl
@Inject
constructor(
    private val databaseInteractor: DatabaseInteractor,
    authenticator: Authenticator,
    private val calendar: Calendar
) : OrderRepository {

    private val currentUserUid = authenticator.getCurrentUserUid()

    override suspend fun createOrder(userOrder: Map<String, Any>): Resource<Status> {

        val timeNow = calendar.time.toString()

        currentUserUid?.let { uid ->

            return try {

                databaseInteractor
                    .createDocumentInCollection(
                        COLLECTION_ORDERS,
                        uid,
                        userOrder,
                        timeNow
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