package com.macoscope.ketchuplunch.view.lunch

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.macoscope.ketchuplunch.di.AccountModule
import com.macoscope.ketchuplunch.di.LunchModule
import com.macoscope.ketchuplunch.di.ScriptModule
import com.macoscope.ketchuplunch.model.lunch.Meal
import com.macoscope.ketchuplunch.presenter.LaunchMenuPresenter
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.withArguments

class LunchMenuFragment : Fragment(), LunchMenuView, AnkoLogger {

    companion object {

        private val ARG_DAY_INDEX = "day_index"

        fun newInstance(sectionNumber: Int): LunchMenuFragment {
            return LunchMenuFragment().withArguments(ARG_DAY_INDEX to sectionNumber)
        }
    }

    val listAdapter: LunchMenuAdapter = LunchMenuAdapter(emptyList<Meal>())

    lateinit var lunchMenuPresenter: LaunchMenuPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val dayIndex: Int = arguments.getInt(ARG_DAY_INDEX)
        lunchMenuPresenter = LunchModule(AccountModule(context), ScriptModule(), context, this)
                .provideLunchMenuPresenter(dayIndex)
        lunchMenuPresenter.createView()
        return LunchMenuUI(listAdapter).createView(AnkoContext.create(ctx, this))
    }

    override fun showMealList(meals: List<Meal>) {
        listAdapter.mealList = meals
        listAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lunchMenuPresenter.destroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lunchMenuPresenter.onActivityResult(requestCode, resultCode)
    }
}


