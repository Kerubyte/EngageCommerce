package com.kerubyte.engagecommerce.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.kerubyte.engagecommerce.data.entity.DatabaseProduct
import com.kerubyte.engagecommerce.data.mapper.NullableInputDatabaseProductMapper
import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.domain.repo.ProductRepository
import com.kerubyte.engagecommerce.infrastructure.Constants.COLLECTION_PRODUCTS
import com.kerubyte.engagecommerce.infrastructure.util.Resource
import com.kerubyte.engagecommerce.infrastructure.util.Status
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
            val querySnapshot = firestore.collection(COLLECTION_PRODUCTS)
                .get()
                .await()
            val databaseProducts = querySnapshot.toObjects(DatabaseProduct::class.java)
            Resource(
                Status.SUCCESS,
                inputDatabaseProductMapper.mapFromDatabaseList(databaseProducts),
                null
            )
        } catch (exc: Exception) {
            Resource(Status.ERROR, null, exc.message)
        }
    }

    override suspend fun getSingleProduct(productUid: String): Resource<Product> {

        return try {

            val documentSnapshot = firestore.collection(COLLECTION_PRODUCTS)
                .document(productUid)
                .get()
                .await()
            val databaseProduct = documentSnapshot.toObject(DatabaseProduct::class.java)
            Resource(
                Status.SUCCESS,
                inputDatabaseProductMapper.mapFromDatabase(databaseProduct),
                null
            )
        } catch (exc: Exception) {
            Resource(Status.ERROR, null, exc.message)
        }
    }

    override suspend fun getProductsFromCart(cartList: List<String>): Resource<List<Product>> {

        return try {

            val documentSnapshot = firestore.collection(COLLECTION_PRODUCTS)
                .whereIn("uid", cartList)
                .get()
                .await()
            val databaseProducts = documentSnapshot.toObjects(DatabaseProduct::class.java)
            val userCart = inputDatabaseProductMapper.mapFromDatabaseList(databaseProducts)
            Resource(
                Status.SUCCESS,
                userCart,
                null
            )
        } catch (exc: Exception) {
            Resource(Status.ERROR, null, exc.message)
        }
    }
}