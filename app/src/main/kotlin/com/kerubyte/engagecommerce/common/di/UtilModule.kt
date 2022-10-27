package com.kerubyte.engagecommerce.common.di

import android.icu.util.Calendar
import com.kerubyte.engagecommerce.common.data.mapper.marketing.OutputCustomerMapper
import com.kerubyte.engagecommerce.common.data.mapper.marketing.OutputEventAttributesMapper
import com.kerubyte.engagecommerce.common.data.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.common.data.mapper.user.NullableOutputDatabaseUserMapper
import com.kerubyte.engagecommerce.common.util.PriceFormatter
import com.kerubyte.engagecommerce.feature.auth.data.util.InputValidator
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

    @Singleton
    @Provides
    fun provideOutputCustomerMapper():
            OutputCustomerMapper = OutputCustomerMapper()

    @Singleton
    @Provides
    fun provideOutputEventAttributesMapper():
            OutputEventAttributesMapper = OutputEventAttributesMapper()

    @Provides
    fun provideCalendarInstance():
            Calendar = Calendar.getInstance()
}