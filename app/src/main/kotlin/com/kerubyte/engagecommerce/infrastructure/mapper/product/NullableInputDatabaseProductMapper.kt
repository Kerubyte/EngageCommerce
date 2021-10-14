package com.kerubyte.engagecommerce.infrastructure.mapper.product

import com.kerubyte.engagecommerce.infrastructure.util.PriceFormatter
import com.kerubyte.engagecommerce.data.entity.DatabaseProduct
import com.kerubyte.engagecommerce.data.mapper.DatabaseMapper
import com.kerubyte.engagecommerce.domain.model.Product
import javax.inject.Inject

interface NullableDatabaseProductMapper<Entity, Model> : DatabaseMapper<Entity?, Model>


class NullableInputDatabaseProductMapper
    @Inject
    constructor(
        private val priceFormatter: PriceFormatter
    ) : NullableDatabaseProductMapper<DatabaseProduct?, Product> {

        fun mapFromDatabase(entity: DatabaseProduct?): Product =
            Product(
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

    fun mapFromDatabaseList(entities: List<DatabaseProduct>?): List<Product> {
        return entities?.map { mapFromDatabase(it) }.orEmpty()
    }

}