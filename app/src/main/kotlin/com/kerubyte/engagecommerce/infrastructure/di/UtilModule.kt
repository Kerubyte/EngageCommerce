package com.kerubyte.engagecommerce.infrastructure.di

import android.icu.util.Calendar
import com.kerubyte.engagecommerce.data.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.data.mapper.user.NullableOutputDatabaseUserMapper
import com.kerubyte.engagecommerce.infrastructure.auth.InputValidator
import com.kerubyte.engagecommerce.infrastructure.util.PriceFormatter
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

    @Provides
    fun provideCalendarInstance():
            Calendar = Calendar.getInstance()
}