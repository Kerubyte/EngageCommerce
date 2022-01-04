package com.kerubyte.engagecommerce.infrastructure.di

import android.app.Application
import android.util.Log
import com.kerubyte.engagecommerce.BuildConfig
import com.user.sdk.UserCom
import com.user.sdk.customer.CustomerUpdateCallback
import com.user.sdk.customer.RegisterResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MarketingModule {

    @Provides
    @Singleton
    fun provideUserCom(application: Application): UserCom.Builder =
        UserCom.Builder(
            application,
            BuildConfig.SDK_TOKEN,
            BuildConfig.API_KEY,
            BuildConfig.APP_DOMAIN
        )
            .trackAllActivities(true)


    @Provides
    @Singleton
    fun provideUserComInstance(): UserCom = UserCom.getInstance()

    @Provides
    @Singleton
    fun provideObjectCustomerCallback() = object : CustomerUpdateCallback {
        override fun onSuccess(p0: RegisterResponse) {
            Log.d("register", "UserCom Registered")
        }

        override fun onFailure(p0: Throwable) {
            Log.d("register", "UserCom Failure")
        }
    }
}