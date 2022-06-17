package com.kerubyte.engagecommerce.common

import android.app.Application
import com.user.sdk.UserCom
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application: Application() {

    @Inject
    lateinit var userComBuilder: UserCom.Builder

    override fun onCreate() {
        super.onCreate()

        userComBuilder.build()
    }
}