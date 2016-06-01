package com.macoscope.ketchuplunch.di

import android.content.Context
import com.macoscope.ketchuplunch.model.ScriptClient
import com.macoscope.ketchuplunch.model.lunch.MealService
import com.macoscope.ketchuplunch.presenter.LaunchMenuPresenter
import com.macoscope.ketchuplunch.view.lunch.LunchMenuView

class LunchModule(val accountModule: AccountModule, val context: Context, val lunchView: LunchMenuView) {

    fun provideMealService(): MealService {
        val googleCredentialWrapper = accountModule.provideGoogleCredentialWrapper()
        val accountRepository = accountModule.provideAccountRepository()
        val mealService = MealService(ScriptClient(googleCredentialWrapper.userCredential), accountRepository.getAccountName())
        return mealService
    }

    fun provideLunchMenuPresenter(dayIndex: Int): LaunchMenuPresenter {
        return LaunchMenuPresenter(provideMealService(), lunchView, dayIndex)
    }
}