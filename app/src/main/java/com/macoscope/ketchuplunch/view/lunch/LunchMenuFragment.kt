package com.macoscope.ketchuplunch.view.lunch

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.model.ScriptClient
import com.macoscope.ketchuplunch.model.login.AccountPreferencesFactory
import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.model.login.GoogleCredentialWrapper
import com.macoscope.ketchuplunch.model.lunch.MealService
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.deferredObservable
import rx.schedulers.Schedulers

class LunchMenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_lunch, container, false)
        val dayIndex = arguments.getInt(ARG_SECTION_NUMBER)
        val textView = rootView.findViewById(R.id.meal_name) as TextView

        deferredObservable {
            val accountRepository = AccountRepository(context, GoogleCredentialWrapper(context), AccountPreferencesFactory(context).getPreferences())
            val mealService = MealService(ScriptClient(accountRepository.getUserCredentials()))
            Observable.from(mealService.getUserMeals())
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    textView.text = it.name
                }

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