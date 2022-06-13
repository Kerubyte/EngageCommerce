package com.kerubyte.engagecommerce.common.di

import android.icu.util.Calendar
import com.kerubyte.engagecommerce.feature.auth.data.util.InputValidator
import com.kerubyte.engagecommerce.common.data.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.common.data.NullableOutputDatabaseUserMapper
import com.kerubyte.engagecommerce.common.util.PriceFormatter
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