package com.macoscope.ketchuplunch.presenter

import android.app.Activity
import com.macoscope.ketchuplunch.model.lunch.Week
import com.macoscope.ketchuplunch.model.lunch.WeeksService
import com.macoscope.ketchuplunch.view.lunch.WeeksView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.deferredObservable
import rx.lang.kotlin.plusAssign
import rx.lang.kotlin.subscriber
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class WeeksPresenter(val weeksService: WeeksService, val weeksView: WeeksView) : AnkoLogger {
    val subscriptions: CompositeSubscription = CompositeSubscription()
    private val REQUEST_AUTHORIZATION = 1001
    private fun loadData() {
        weeksView.showLoading()
        subscriptions += loadWeeksObservable().subscribe(
                subscriber<List<Week>>()
                        .onNext {
                            weeksView.showWeeks(it)
                            weeksView.hideLoading()
                        }
                        .onError {
                            error("", it)
                            weeksView.hideLoading()
                        }
        )
    }

    private fun loadWeeksObservable(): Observable<List<Week>> {
        return deferredObservable {
            Observable.from(weeksService.getWeeks())
        }.toList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun onActivityResult(requestCode: Int, resultCode: Int) {
        when (requestCode) {
            REQUEST_AUTHORIZATION -> {
                if (resultCode == Activity.RESULT_OK) {
                    loadData()
                }
            }
        }
    }

    fun createView() {
        loadData()
    }

    fun destroyView() {
        subscriptions.clear()
    }
}