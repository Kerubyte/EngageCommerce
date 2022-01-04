package com.kerubyte.engagecommerce.infrastructure.di

import android.icu.util.Calendar
import com.kerubyte.engagecommerce.infrastructure.auth.InputValidator
import com.kerubyte.engagecommerce.infrastructure.mapper.marketing.OutputCustomerMapper
import com.kerubyte.engagecommerce.infrastructure.mapper.marketing.OutputEventAttributesMapper
import com.kerubyte.engagecommerce.infrastructure.mapper.marketing.OutputProductAttributesMapper
import com.kerubyte.engagecommerce.infrastructure.mapper.user.NullableInputDatabaseUserMapper
import com.kerubyte.engagecommerce.infrastructure.mapper.user.NullableOutputDatabaseUserMapper
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

    @Singleton
    @Provides
    fun provideOutputProductAttributesMapper():
            OutputProductAttributesMapper = OutputProductAttributesMapper()

    @Singleton
    @Provides
    fun provideOutputEventAttributesMapper():
            OutputEventAttributesMapper = OutputEventAttributesMapper()

    @Singleton
    @Provides
    fun provideOutputCustomerMapper():
            OutputCustomerMapper = OutputCustomerMapper()

    @Provides
    fun provideCalendarInstance():
            Calendar = Calendar.getInstance()
}