package com.example.engagecommerce.infrastructure.di

import android.util.Log
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

    @Singleton
    @Provides
    fun provideUserComInstance(): UserCom = UserCom.getInstance()

    @Provides
    fun provideObjectCustomerCallback() = object : CustomerUpdateCallback {
        override fun onSuccess(p0: RegisterResponse) {
            Log.d("register", "UserCom Registered")
        }

        override fun onFailure(p0: Throwable) {
            Log.d("register", "UserCom Failure")
        }
    }
}
