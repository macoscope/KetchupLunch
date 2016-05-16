package com.macoscope.ketchuplunch

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class KetchupApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }
}