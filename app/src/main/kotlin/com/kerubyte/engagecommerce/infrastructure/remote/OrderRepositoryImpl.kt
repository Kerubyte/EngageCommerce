package com.kerubyte.engagecommerce.infrastructure.remote

import android.icu.util.Calendar
import com.kerubyte.engagecommerce.data.database.Authenticator
import com.kerubyte.engagecommerce.data.database.DatabaseInteractor
import com.kerubyte.engagecommerce.data.repository.OrderRepository
import com.kerubyte.engagecommerce.data.util.DispatcherProvider
import com.kerubyte.engagecommerce.infrastructure.Constants.COLLECTION_ORDERS
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepositoryImpl
@Inject
constructor(
    private val databaseInteractor: DatabaseInteractor,
    authenticator: Authenticator,
    private val dispatcherProvider: DispatcherProvider,
    private val calendar: Calendar
) : OrderRepository {

    private val currentUserUid = authenticator.getCurrentUserUid()

    override suspend fun createOrder(userOrder: Map<String, Any>): Resource<Nothing> =

        withContext(dispatcherProvider.io) {

            val timeNow = calendar.time.toString()

            currentUserUid?.let { uid ->

                try {

                    databaseInteractor
                        .createDocumentInCollection(
                            COLLECTION_ORDERS,
                            uid,
                            userOrder,
                            timeNow
                        )
                        .await()

                    Resource.Success(null)
                } catch (exc: Exception) {
                    Resource.Error.NetworkError(exc.message)
                }
            } ?: Resource.Error.AuthenticationError(null)
        }
}