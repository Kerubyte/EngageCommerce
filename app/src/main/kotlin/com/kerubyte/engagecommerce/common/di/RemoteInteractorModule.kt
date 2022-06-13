package com.kerubyte.engagecommerce.common.di

import com.kerubyte.engagecommerce.common.data.DatabaseInteractorImpl
import com.kerubyte.engagecommerce.feature.auth.domain.Authenticator
import com.kerubyte.engagecommerce.common.domain.DatabaseInteractor
import com.kerubyte.engagecommerce.feature.auth.data.AuthenticatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteInteractorModule {

    @Singleton
    @Binds
    abstract fun bindAuthenticatorService(
        authenticatorImpl: AuthenticatorImpl
    ) : Authenticator

    @Singleton
    @Binds
    abstract fun bindDatabaseInteractor(
        databaseInteractorImpl: DatabaseInteractorImpl
    ) : DatabaseInteractor
}