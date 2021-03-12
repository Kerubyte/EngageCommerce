package com.example.engagecommerce

import android.app.Application
import com.example.engagecommerce.utils.Keys
import com.user.sdk.UserCom

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