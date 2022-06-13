package com.kerubyte.engagecommerce.common.di

import com.kerubyte.engagecommerce.common.data.OrderRepositoryImpl
import com.kerubyte.engagecommerce.common.data.ProductRepositoryImpl
import com.kerubyte.engagecommerce.common.data.UserRepositoryImpl
import com.kerubyte.engagecommerce.common.domain.OrderRepository
import com.kerubyte.engagecommerce.common.domain.ProductRepository
import com.kerubyte.engagecommerce.common.domain.UserRepository
import com.kerubyte.engagecommerce.feature.auth.data.AuthRepositoryImpl
import com.kerubyte.engagecommerce.feature.auth.domain.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @ActivityRetainedScoped
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @ActivityRetainedScoped
    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @ActivityRetainedScoped
    @Binds
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}