package com.kerubyte.engagecommerce.infrastructure.di

import com.kerubyte.engagecommerce.data.repository.MarketingRepository
import com.kerubyte.engagecommerce.infrastructure.remote.OrderRepositoryImpl
import com.kerubyte.engagecommerce.infrastructure.remote.ProductRepositoryImpl
import com.kerubyte.engagecommerce.infrastructure.remote.UserRepositoryImpl
import com.kerubyte.engagecommerce.data.repository.OrderRepository
import com.kerubyte.engagecommerce.data.repository.ProductRepository
import com.kerubyte.engagecommerce.data.repository.UserRepository
import com.kerubyte.engagecommerce.infrastructure.remote.MarketingRepositoryImpl
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
    abstract fun bindUserRepo(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

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

    @ActivityRetainedScoped
    @Binds
    abstract fun bindMarketingRepository(
        marketingRepositoryImpl: MarketingRepositoryImpl
    ): MarketingRepository
}