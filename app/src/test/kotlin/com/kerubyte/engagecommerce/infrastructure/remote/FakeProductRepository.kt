package com.kerubyte.engagecommerce.infrastructure.remote

import com.kerubyte.engagecommerce.data.repository.ProductRepository
import com.kerubyte.engagecommerce.domain.model.Product
import com.kerubyte.engagecommerce.infrastructure.util.Result
import com.kerubyte.engagecommerce.infrastructure.util.testProducts

class FakeProductRepository : ProductRepository {

    override suspend fun getAllProducts(): Result<List<Product>> {
        return Result.Success(testProducts)

    }

    override suspend fun getSingleProduct(productUid: String): Result<Product> {
        val singleProduct = testProducts.find { it.uid == productUid }
        return Result.Success(singleProduct)

    }

    override suspend fun getProductsFromCart(cartList: List<String>): Result<List<Product>> {
        val productsInCart = testProducts.filter { product ->
            product.uid in cartList
        }
        return if (productsInCart.isNotEmpty()) Result.Success(productsInCart)
        else Result.Error.NetworkError(null)
    }
}