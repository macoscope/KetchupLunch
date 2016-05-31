package com.macoscope.ketchuplunch.di

import android.content.Context
import com.macoscope.ketchuplunch.model.ScriptClient
import com.macoscope.ketchuplunch.model.lunch.MealService
import com.macoscope.ketchuplunch.presenter.LaunchMenuPresenter
import com.macoscope.ketchuplunch.view.lunch.LunchMenuView

class LunchModule(val accountModule: AccountModule,val context: Context, val lunchView: LunchMenuView) {

     fun provideMealService(): MealService {
         val accountRepository = accountModule.provideAccountRepository()
        val mealService = MealService(ScriptClient(accountRepository.getUserCredentials()), accountRepository.getAccountName())
        return mealService
    }

    fun provideLunchMenuPresenter(dayIndex: Int): LaunchMenuPresenter {
        return LaunchMenuPresenter(provideMealService(), lunchView, dayIndex)
    }
}