package com.example.engagecommerce.data.database.mappers

import com.example.engagecommerce.data.entity.ProductEntity
import com.example.engagecommerce.domain.model.Product
import javax.inject.Inject


class NullableOutputProductEntityMapper
@Inject
constructor() : NullableProductEntityMapper<ProductEntity, Product> {

    fun mapToEntity(model: Product): ProductEntity {
        return ProductEntity(
            uid = model.uid,
            name = model.name,
            brand = model.brand,
            price = model.price,
            category = model.category,
            description = model.description,
            details = model.details,
            imageUrl = model.imageUrl,
            delivery = model.delivery
        )
    }

    fun mapToEntityList(models: List<Product>): List<ProductEntity>? {
        return if (models.isEmpty()) null else models.map { mapToEntity(it) }
    }
}