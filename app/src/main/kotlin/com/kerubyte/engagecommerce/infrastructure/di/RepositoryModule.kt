package com.kerubyte.engagecommerce.infrastructure.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kerubyte.engagecommerce.data.mapper.NullableInputDatabaseProductMapper
import com.kerubyte.engagecommerce.data.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.data.mapper.user.NullableOutputDatabaseUserMapper
import com.kerubyte.engagecommerce.data.remote.ProductRepositoryImpl
import com.kerubyte.engagecommerce.data.remote.UserRepositoryImpl
import com.kerubyte.engagecommerce.domain.repo.ProductRepository
import com.kerubyte.engagecommerce.domain.repo.UserRepository
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

    @Singleton
    @Provides
    fun provideUserRepository(
        firestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth,
        inputDatabaseUserMapper: NullableInputDatabaseUserMapper,
        outputDatabaseUserMapper: NullableOutputDatabaseUserMapper
    ): UserRepository = UserRepositoryImpl(
        firestore, firebaseAuth, inputDatabaseUserMapper, outputDatabaseUserMapper
    )
}