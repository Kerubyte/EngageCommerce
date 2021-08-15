package com.kerubyte.engagecommerce.infrastructure.di

import com.google.firebase.firestore.FirebaseFirestore
import com.kerubyte.engagecommerce.application.repo.ProductRepository
import com.kerubyte.engagecommerce.application.utils.mapper.NullableInputDatabaseProductMapper
import com.kerubyte.engagecommerce.data.remote.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(
        firestore: FirebaseFirestore,
        inputDatabaseProductMapper: NullableInputDatabaseProductMapper
    ): ProductRepository = ProductRepositoryImpl(firestore, inputDatabaseProductMapper)
}