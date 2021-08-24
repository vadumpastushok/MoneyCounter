package com.example.moneycounter.app

import android.app.Application
import android.content.Context
import com.example.moneycounter.R
import com.example.moneycounter.model.entity.ui.CategoryTest
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