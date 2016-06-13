package com.macoscope.ketchuplunch.di

import android.content.Context
import com.macoscope.ketchuplunch.LogOutUseCase
import com.macoscope.ketchuplunch.model.ScriptClient
import com.macoscope.ketchuplunch.model.lunch.WeeksService
import com.macoscope.ketchuplunch.presenter.WeeksPresenter
import com.macoscope.ketchuplunch.view.lunch.WeeksView

class WeekModule(context: Context, val scriptModule: ScriptModule, val weeksView: WeeksView) : AccountModule
(context) {

    companion object {
        var scriptClient: ScriptClient? = null
    }

    private fun provideWeeksService(): WeeksService = WeeksService(provideScriptClient())

    private fun provideScriptClient(): ScriptClient {
        return scriptClient ?: ScriptClient(provideGoogleCredentialWrapper().userCredential,
                scriptModule.provideRootUrl())
    }

    private fun provideLogOutUseCase(): LogOutUseCase = LogOutUseCase(provideGoogleCredentialWrapper(), provideAccountRepository())


    fun provideWeeksPresenter(): WeeksPresenter {
        return WeeksPresenter(provideWeeksService(), weeksView, provideLogOutUseCase())
    }
}