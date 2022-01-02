package com.kerubyte.engagecommerce.presentation.ui

import android.app.Application
import com.user.sdk.UserCom
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application: Application() {

    @Inject
    lateinit var provideUserCom: UserCom.Builder

    override fun onCreate() {
        super.onCreate()

        provideUserCom.build()
    }
}