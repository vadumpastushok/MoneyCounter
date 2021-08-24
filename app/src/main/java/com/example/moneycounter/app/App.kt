package com.example.moneycounter.app

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        context = this
    }

    companion object {
        @JvmStatic
        var context: Context by Delegates.notNull()
            private set

    }
}