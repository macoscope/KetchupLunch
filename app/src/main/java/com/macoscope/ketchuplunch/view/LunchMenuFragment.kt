package com.macoscope.ketchuplunch.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.util.ExponentialBackOff
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.model.MealService
import com.macoscope.ketchuplunch.model.ScriptClient

class LunchMenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_lunch, container, false)
        val dayIndex = arguments.getInt(ARG_SECTION_NUMBER)
        val accountName = "darek@macoscope.net"
        val googleAccountCredential = GoogleAccountCredential.usingOAuth2(context.applicationContext,
                listOf("https://www.googleapis.com/auth/spreadsheets"))
        googleAccountCredential.selectedAccountName = accountName
        googleAccountCredential.backOff = ExponentialBackOff()
        val mealService = MealService(ScriptClient(googleAccountCredential))

        val textView = rootView.findViewById(R.id.meal_name) as TextView

        textView.text = mealService.getUserMeals().first().name
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