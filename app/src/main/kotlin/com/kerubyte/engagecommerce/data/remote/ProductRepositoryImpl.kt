package com.kerubyte.engagecommerce.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.kerubyte.engagecommerce.application.repo.ProductRepository
import com.kerubyte.engagecommerce.application.utils.Constants
import com.kerubyte.engagecommerce.application.utils.Resource
import com.kerubyte.engagecommerce.application.utils.Status
import com.kerubyte.engagecommerce.application.utils.mapper.NullableInputDatabaseProductMapper
import com.kerubyte.engagecommerce.domain.model.database.DatabaseProduct
import com.kerubyte.engagecommerce.domain.model.local.Product
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val inputDatabaseProductMapper: NullableInputDatabaseProductMapper
) : ProductRepository {

    override suspend fun getAllProducts(): Resource<List<Product>> {

        return try {
            val querySnapshot = firestore.collection(Constants.COLLECTION_PRODUCTS)
                .get()
                .await()
            val result = querySnapshot.toObjects(DatabaseProduct::class.java)
            Resource(Status.SUCCESS, inputDatabaseProductMapper.mapFromDatabaseList(result), null)
        } catch (exc: Exception) {
            Resource(Status.ERROR, null, exc.message)
        }
    }
}