package com.kerubyte.engagecommerce.infrastructure.di

import com.kerubyte.engagecommerce.infrastructure.util.PriceFormatter
import com.kerubyte.engagecommerce.infrastructure.auth.InputValidator
import com.kerubyte.engagecommerce.data.mapper.User.NullableDatabaseUserMapper
import com.kerubyte.engagecommerce.data.mapper.User.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.domain.model.User
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