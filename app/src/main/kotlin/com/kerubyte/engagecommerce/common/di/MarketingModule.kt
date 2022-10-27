package com.kerubyte.engagecommerce.common.di

import android.app.Application
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

    @Provides
    @Singleton
    fun provideUserComBuilder(application: Application): UserCom.Builder =
        UserCom.Builder(
            application,
            "aAEb78j0v3y0DgjHuSl1DL2qB4TSJHby5i69Ldv9xJSdiTs0PpAl2fXDgjlWEMfo",
            "4F8oIb",
            "https://engagecommerce.user.com/"
        )
            .trackAllActivities(true)


    @Provides
    @Singleton
    fun provideUserComInstance(): UserCom = UserCom.getInstance()

    @Provides
    @Singleton
    fun provideObjectCustomerCallback() = object : CustomerUpdateCallback {
        override fun onSuccess(p0: RegisterResponse) {
            TODO()
        }

        override fun onFailure(p0: Throwable) {
            TODO()
        }
    }
}