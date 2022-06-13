package com.kerubyte.engagecommerce.common.data

import com.kerubyte.engagecommerce.common.domain.DatabaseInteractor
import com.kerubyte.engagecommerce.common.data.entity.DatabaseProduct
import com.kerubyte.engagecommerce.common.domain.ProductRepository
import com.kerubyte.engagecommerce.common.util.DispatcherProvider
import com.kerubyte.engagecommerce.common.domain.model.Product
import com.kerubyte.engagecommerce.common.util.Constants.COLLECTION_PRODUCTS
import com.kerubyte.engagecommerce.feature.product.NullableInputDatabaseProductMapper
import com.kerubyte.engagecommerce.common.util.Result
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl
@Inject
constructor(
    private val databaseInteractor: DatabaseInteractor,
    private val dispatcherProvider: DispatcherProvider,
    private val inputDatabaseProductMapper: NullableInputDatabaseProductMapper
) : ProductRepository {

    override suspend fun getAllProducts(): Result<List<Product>> =

        withContext(dispatcherProvider.io) {
            val querySnapshot = databaseInteractor
                .getWholeCollection(COLLECTION_PRODUCTS)
                .await()
            val databaseProducts = querySnapshot.toObjects(DatabaseProduct::class.java)
            val result = inputDatabaseProductMapper.mapFromDatabaseList(databaseProducts)

            Result.Success(result)
        }

    override suspend fun getSingleProduct(productUid: String): Result<Product> =

        withContext(dispatcherProvider.io) {
            val documentSnapshot = databaseInteractor
                .getSingleDocument(COLLECTION_PRODUCTS, productUid)
                .await()
            val databaseProduct = documentSnapshot.toObject(DatabaseProduct::class.java)
            val result = inputDatabaseProductMapper.mapFromDatabase(databaseProduct)

            Result.Success(result)
        }

    override suspend fun getProductsFromCart(cartList: List<String>): Result<List<Product>> =

        if (cartList.isEmpty()) Result.Success(emptyList())
        else

            withContext(dispatcherProvider.io) {
                val documentSnapshot = databaseInteractor
                    .getItemsFromCollection(COLLECTION_PRODUCTS, cartList)
                    .await()
                val databaseProducts = documentSnapshot.toObjects(DatabaseProduct::class.java)
                val userCart = inputDatabaseProductMapper.mapFromDatabaseList(databaseProducts)

                Result.Success(userCart)
            }
}