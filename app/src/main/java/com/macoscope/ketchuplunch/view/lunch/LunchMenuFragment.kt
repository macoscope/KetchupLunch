package com.macoscope.ketchuplunch.view.lunch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.macoscope.ketchuplunch.model.ScriptClient
import com.macoscope.ketchuplunch.model.login.AccountPreferencesFactory
import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.model.login.GoogleCredentialWrapper
import com.macoscope.ketchuplunch.model.lunch.Meal
import com.macoscope.ketchuplunch.model.lunch.MealService
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.support.v4.ctx
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.deferredObservable
import rx.lang.kotlin.subscriber
import rx.schedulers.Schedulers

class LunchMenuFragment : Fragment(), AnkoLogger {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mealList = emptyList<Meal>()
        val listAdapter = LunchMenuAdapter(mealList)
        val rootView = LunchMenuUI(listAdapter).createView(AnkoContext.create(ctx, this))
        val dayIndex = arguments.getInt(ARG_SECTION_NUMBER)


        deferredObservable {
            val accountRepository = AccountRepository(context, GoogleCredentialWrapper(context),
                    AccountPreferencesFactory(context).getPreferences())
            val mealService = MealService(ScriptClient(accountRepository.getUserCredentials()), accountRepository.getAccountName())
            Observable.from(mealService.getUserMeals(dayIndex))
        }.toList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        subscriber<List<Meal>>().onNext {
                            listAdapter.mealList = it
                            listAdapter.notifyDataSetChanged()
                        }.onError {
                            error("", it)
                            //TODO show empty view
                            listAdapter.mealList = emptyList()
                            listAdapter.notifyDataSetChanged()
                        }
                )
        return rootView
    }

    companion object {

        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): LunchMenuFragment {
            val fragment = LunchMenuFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }
    }
}
