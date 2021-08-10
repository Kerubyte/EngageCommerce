package com.kerubyte.engagecommerce.application.utils.mapper

import com.kerubyte.engagecommerce.domain.model.database.DatabaseProduct
import com.kerubyte.engagecommerce.domain.model.local.Product
import javax.inject.Inject

interface NullableDatabaseProductMapper<Entity, Model> : DatabaseProductMapper<Entity?, Model>


class NullableInputDatabaseProductMapper
    @Inject
    constructor() : NullableDatabaseProductMapper<DatabaseProduct?, Product> {

        fun mapFromDatabase(entity: DatabaseProduct?): Product =
            Product(
                uid = entity?.uid.orEmpty(),
                name = entity?.name.orEmpty(),
                brand = entity?.brand.orEmpty(),
                price = entity?.price ?: 0.00,
                formattedPrice = entity?.price.toString(),
                category = entity?.category.orEmpty(),
                description = entity?.description.orEmpty(),
                imageUrl = entity?.imageUrl.orEmpty(),
                delivery = entity?.delivery ?: false
            )

    fun mapFromDatabaseList(entities: List<DatabaseProduct>?): List<Product> {
        return entities?.map { mapFromDatabase(it) }.orEmpty()
    }

}