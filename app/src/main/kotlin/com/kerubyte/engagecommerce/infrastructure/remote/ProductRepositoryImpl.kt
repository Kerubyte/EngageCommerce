package com.kerubyte.engagecommerce.infrastructure.remote

import com.kerubyte.engagecommerce.data.database.DatabaseInteractor
import com.kerubyte.engagecommerce.data.entity.DatabaseProduct
import com.kerubyte.engagecommerce.data.repository.ProductRepository
import com.kerubyte.engagecommerce.data.util.DispatcherProvider
import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.infrastructure.Constants.COLLECTION_PRODUCTS
import com.kerubyte.engagecommerce.infrastructure.mapper.product.NullableInputDatabaseProductMapper
import com.kerubyte.engagecommerce.infrastructure.util.Result
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

            try {
                val querySnapshot = databaseInteractor
                    .getWholeCollection(COLLECTION_PRODUCTS)
                    .await()
                val databaseProducts = querySnapshot.toObjects(DatabaseProduct::class.java)
                val result = inputDatabaseProductMapper.mapFromDatabaseList(databaseProducts)

                Result.Success(result)
            } catch (exc: Exception) {
                Result.Error.NetworkError(exc.message)
            }
        }

    override suspend fun getSingleProduct(productUid: String): Result<Product> =

        withContext(dispatcherProvider.io) {

            try {
                val documentSnapshot = databaseInteractor
                    .getSingleDocument(COLLECTION_PRODUCTS, productUid)
                    .await()
                val databaseProduct = documentSnapshot.toObject(DatabaseProduct::class.java)
                val result = inputDatabaseProductMapper.mapFromDatabase(databaseProduct)

                Result.Success(result)

            } catch (exc: Exception) {
                Result.Error.NetworkError(exc.message)
            }
        }

    override suspend fun getProductsFromCart(cartList: List<String>): Result<List<Product>> =

        if (cartList.isEmpty()) Result.Success(emptyList())
        else

            withContext(dispatcherProvider.io) {

                try {
                    val documentSnapshot = databaseInteractor
                        .getItemsFromCollection(COLLECTION_PRODUCTS, cartList)
                        .await()
                    val databaseProducts = documentSnapshot.toObjects(DatabaseProduct::class.java)
                    val userCart = inputDatabaseProductMapper.mapFromDatabaseList(databaseProducts)

                    Result.Success(userCart)
                } catch (exc: Exception) {
                    Result.Error.NetworkError(exc.message)
                }
            }
}