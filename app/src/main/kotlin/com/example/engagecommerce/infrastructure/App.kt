package com.example.engagecommerce.infrastructure

import android.app.Application
import com.example.engagecommerce.application.util.Keys
import com.user.sdk.UserCom
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UserCom.Builder(
            this,
            Keys.apiKey,
            Keys.domainKey,
            Keys.baseUrl
        )
            .trackAllActivities(true)
            .build()
    }
}