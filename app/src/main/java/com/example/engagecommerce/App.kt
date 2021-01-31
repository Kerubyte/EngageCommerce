package com.example.engagecommerce

import android.app.Application
import com.user.sdk.UserCom

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UserCom.Builder(
            this,
            "EweqN0IbQoDB7Nlud6pV2K35fAJa7VvQ4vMs9Mi93STX1vvgSXoOnVNFfPqZNpe4",
            "4F8oIb",
            "https://engagecommerce.user.com/"
        )
            .trackAllActivities(true)
            .build()
    }

}