package com.kerubyte.engagecommerce.infrastructure.di

import com.kerubyte.engagecommerce.data.remote.OrderRepositoryImpl
import com.kerubyte.engagecommerce.data.remote.ProductRepositoryImpl
import com.kerubyte.engagecommerce.data.remote.UserRepositoryImpl
import com.kerubyte.engagecommerce.domain.repo.OrderRepository
import com.kerubyte.engagecommerce.domain.repo.ProductRepository
import com.kerubyte.engagecommerce.domain.repo.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindUserRepo(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @ActivityRetainedScoped
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @ActivityRetainedScoped
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}