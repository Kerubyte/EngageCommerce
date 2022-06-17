package com.kerubyte.engagecommerce.common.data.mapper.product

import com.kerubyte.engagecommerce.common.util.PriceFormatter
import com.kerubyte.engagecommerce.common.data.entity.ProductEntity
import com.kerubyte.engagecommerce.common.util.DatabaseMapper
import com.kerubyte.engagecommerce.common.domain.model.ProductModel
import javax.inject.Inject

interface NullableDatabaseProductMapper<Entity, Model> : DatabaseMapper<Entity?, Model>


class NullableInputDatabaseProductMapper
    @Inject
    constructor(
        private val priceFormatter: PriceFormatter
    ) : NullableDatabaseProductMapper<ProductEntity?, ProductModel> {

        fun mapFromDatabase(entity: ProductEntity?): ProductModel =
            ProductModel(
                uid = entity?.uid.orEmpty(),
                name = entity?.name.orEmpty(),
                brand = entity?.brand.orEmpty(),
                price = entity?.price ?: 0.00,
                formattedPrice = priceFormatter.formatPrice(entity?.price),
                category = entity?.category.orEmpty(),
                description = entity?.description.orEmpty(),
                imageUrl = entity?.imageUrl.orEmpty(),
                delivery = entity?.delivery ?: false
            )

    fun mapFromDatabaseList(entities: List<ProductEntity>?): List<ProductModel> {
        return entities?.map { mapFromDatabase(it) }.orEmpty()
    }

}