package com.macoscope.ketchuplunch.di

import android.content.Context
import com.macoscope.ketchuplunch.model.ScriptClient
import com.macoscope.ketchuplunch.model.lunch.MealService
import com.macoscope.ketchuplunch.presenter.LaunchMenuPresenter
import com.macoscope.ketchuplunch.view.lunch.LunchMenuView

class LunchModule(val accountModule: AccountModule, val context: Context, val lunchView: LunchMenuView) {

    companion object {
        var scriptClient: ScriptClient? = null
    }

    fun provideMealService(): MealService {
        val accountRepository = accountModule.provideAccountRepository()
        val mealService = MealService(provideScriptClient(), accountRepository.getAccountName())
        return mealService
    }

    fun provideScriptClient(): ScriptClient {
        val googleCredentialWrapper = accountModule.provideGoogleCredentialWrapper()
        return scriptClient ?: ScriptClient(googleCredentialWrapper.userCredential)
    }

    fun provideLunchMenuPresenter(dayIndex: Int): LaunchMenuPresenter {
        return LaunchMenuPresenter(provideMealService(), lunchView, dayIndex)
    }
}