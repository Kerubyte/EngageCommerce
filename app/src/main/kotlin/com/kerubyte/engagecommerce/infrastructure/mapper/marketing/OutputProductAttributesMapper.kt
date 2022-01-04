package com.kerubyte.engagecommerce.infrastructure.mapper.marketing

import com.kerubyte.engagecommerce.data.mapper.DatabaseMapper
import com.kerubyte.engagecommerce.domain.model.Product

class OutputProductAttributesMapper : DatabaseMapper<Product, Map<String, Any>> {

    fun mapFromProduct(product: Product): Map<String, Any> =

        mapOf(
            "name" to product.name,
            "price" to product.price,
            "brand_name" to product.brand,
            "category" to product.category,
            "image_url" to product.imageUrl
        )
}