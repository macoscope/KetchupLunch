package com.macoscope.ketchuplunch.presenter

import android.app.Activity
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.macoscope.ketchuplunch.model.lunch.Meal
import com.macoscope.ketchuplunch.model.lunch.MealService
import com.macoscope.ketchuplunch.view.lunch.LunchMenuView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.deferredObservable
import rx.lang.kotlin.plusAssign
import rx.lang.kotlin.subscriber
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class LaunchMenuPresenter(val mealService: MealService, val lunchMenuView: LunchMenuView, val dayIndex: Int) : AnkoLogger {
    private val REQUEST_AUTHORIZATION = 1001
    val subscriptions: CompositeSubscription = CompositeSubscription()

    private fun loadData(dayIndex: Int) {
        subscriptions += loadUserMealsForDayObservable(dayIndex)
                .subscribe (
                        subscriber<List<Meal>>().onNext {
                            lunchMenuView.showMealList(it)

                        }.onError {
                            error("", it)
                            if (it is UserRecoverableAuthIOException) {
                                lunchMenuView.startActivityForResult(it.intent, REQUEST_AUTHORIZATION);
                            }
                            //TODO show empty view
                            lunchMenuView.showMealList(emptyList())
                        }
                )
    }

    private fun loadUserMealsForDayObservable(dayIndex: Int): Observable<MutableList<Meal>> {
        return deferredObservable {
            Observable.from(mealService.getUserMeals(dayIndex))
        }.toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {

        when (requestCode) {
            REQUEST_AUTHORIZATION -> {
                if (resultCode == Activity.RESULT_OK) {
                    loadData(dayIndex)
                }
            }
        }
    }

    fun createView() {
        loadData(dayIndex)
    }

    fun destroyView() {
        subscriptions.clear()
    }
}