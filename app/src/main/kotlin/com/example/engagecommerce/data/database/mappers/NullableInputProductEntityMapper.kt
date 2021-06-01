package com.example.engagecommerce.data.database.mappers

import com.example.engagecommerce.application.util.PriceFormatter
import com.example.engagecommerce.application.util.ProductEntityMapper
import com.example.engagecommerce.data.entity.ProductEntity
import com.example.engagecommerce.domain.model.Product
import javax.inject.Inject

interface NullableProductEntityMapper<Entity, Model> : ProductEntityMapper<Entity?, Model>


class NullableInputProductEntityMapper
@Inject
constructor(
    private val priceFormatter: PriceFormatter
) : NullableProductEntityMapper<ProductEntity?, Product> {

    fun mapFromEntity(entity: ProductEntity?): Product {
        return Product(
            uid = entity?.uid.orEmpty(),
            name = entity?.name.orEmpty(),
            brand = entity?.brand.orEmpty(),
            price = entity?.price ?: 0L,
            formattedPrice = entity?.price?.let { priceFormatter.formatPrice(it) }.orEmpty(),
            category = entity?.category.orEmpty(),
            description = entity?.description.orEmpty(),
            imageUrl = entity?.imageUrl.orEmpty(),
            delivery = entity?.delivery ?: false
        )
    }

    fun mapFromEntityList(entities: List<ProductEntity>?): List<Product> {
        return entities?.map { mapFromEntity(it) }.orEmpty()
    }
}