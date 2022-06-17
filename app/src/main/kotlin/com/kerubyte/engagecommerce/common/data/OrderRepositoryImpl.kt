package com.kerubyte.engagecommerce.common.data

import android.icu.util.Calendar
import com.kerubyte.engagecommerce.feature.auth.domain.Authenticator
import com.kerubyte.engagecommerce.common.domain.DatabaseInteractor
import com.kerubyte.engagecommerce.common.domain.OrderRepository
import com.kerubyte.engagecommerce.common.domain.DispatcherProvider
import com.kerubyte.engagecommerce.common.util.Constants.COLLECTION_ORDERS
import com.kerubyte.engagecommerce.common.util.Result
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

    override suspend fun createOrder(userOrder: Map<String, Any>): Result<Any> =

        withContext(dispatcherProvider.io) {
            val timeNow = calendar.time.toString()

            currentUserUid?.let { uid ->
                databaseInteractor
                    .createDocumentInCollection(
                        COLLECTION_ORDERS,
                        uid,
                        userOrder,
                        timeNow
                    )
                    .await()

                Result.Success(null)
            } ?: Result.Error.AuthenticationError(null)
        }
}