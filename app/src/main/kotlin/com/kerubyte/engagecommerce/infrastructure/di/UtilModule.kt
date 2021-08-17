package com.kerubyte.engagecommerce.infrastructure.di

import com.kerubyte.engagecommerce.infrastructure.util.PriceFormatter
import com.kerubyte.engagecommerce.infrastructure.auth.InputValidator
import com.kerubyte.engagecommerce.data.mapper.user.NullableDatabaseUserMapper
import com.kerubyte.engagecommerce.data.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.data.entity.DatabaseUser
import com.kerubyte.engagecommerce.data.mapper.DatabaseMapper
import com.kerubyte.engagecommerce.data.mapper.user.NullableOutputDatabaseUserMapper
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
            NullableInputDatabaseUserMapper = NullableInputDatabaseUserMapper()

    @Singleton
    @Provides
    fun provideNullableOutputDatabaseUserMapper():
            NullableOutputDatabaseUserMapper = NullableOutputDatabaseUserMapper()
}