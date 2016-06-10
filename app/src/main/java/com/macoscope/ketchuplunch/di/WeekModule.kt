package com.macoscope.ketchuplunch.di

import com.macoscope.ketchuplunch.model.ScriptClient
import com.macoscope.ketchuplunch.model.lunch.WeeksService
import com.macoscope.ketchuplunch.presenter.WeeksPresenter
import com.macoscope.ketchuplunch.view.lunch.WeeksView

class WeekModule(val accountModule: AccountModule, val scriptModule: ScriptModule, val weeksView: WeeksView) {

    companion object {
        var scriptClient: ScriptClient? = null
    }

    private fun provideWeeksService(): WeeksService =  WeeksService(provideScriptClient())

    private fun provideScriptClient(): ScriptClient {
        val googleCredentialWrapper = accountModule.provideGoogleCredentialWrapper()
        return scriptClient ?: ScriptClient(googleCredentialWrapper.userCredential, scriptModule.provideRootUrl())
    }

    fun provideWeeksPresenter(): WeeksPresenter {
        return WeeksPresenter(provideWeeksService(), weeksView)
    }
}