package com.kerubyte.engagecommerce.infrastructure.di

import com.kerubyte.engagecommerce.application.utils.PriceFormatter
import com.kerubyte.engagecommerce.application.utils.auth.InputValidator
import com.kerubyte.engagecommerce.application.utils.mapper.User.NullableDatabaseUserMapper
import com.kerubyte.engagecommerce.application.utils.mapper.User.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.domain.model.database.DatabaseUser
import com.kerubyte.engagecommerce.domain.model.local.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Singleton
    @Provides
    fun providePriceFormatter(): PriceFormatter = PriceFormatter()

    @Singleton
    @Provides
    fun provideInputValidator(): InputValidator = InputValidator

    @Singleton
    @Provides
    fun provideNullableInputDatabaseUserMapper():
            NullableDatabaseUserMapper<DatabaseUser, User> = NullableInputDatabaseUserMapper()
}