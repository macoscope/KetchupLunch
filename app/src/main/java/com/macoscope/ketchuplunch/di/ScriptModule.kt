package com.macoscope.ketchuplunch.di

import com.google.api.services.script.Script

class ScriptModule() {

    companion object {
        var rootUrl: String? = null
    }

    fun provideRootUrl(): String {
        return rootUrl ?: Script.DEFAULT_ROOT_URL
    }
}