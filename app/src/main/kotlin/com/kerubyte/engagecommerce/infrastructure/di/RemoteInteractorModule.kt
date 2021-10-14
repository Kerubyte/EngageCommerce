package com.kerubyte.engagecommerce.infrastructure.di

import com.kerubyte.engagecommerce.data.database.Authenticator
import com.kerubyte.engagecommerce.data.database.DatabaseInteractor
import com.kerubyte.engagecommerce.infrastructure.remote.interactor.AuthenticatorImpl
import com.kerubyte.engagecommerce.infrastructure.remote.interactor.DatabaseInteractorImpl
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