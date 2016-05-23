package com.macoscope.ketchuplunch.view.lunch

import android.content.Intent
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
import com.macoscope.ketchuplunch.presenter.LaunchMenuPresenter
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.withArguments

class LunchMenuFragment : Fragment(), LunchMenuView, AnkoLogger {
    companion object {

        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(sectionNumber: Int): LunchMenuFragment {
            return LunchMenuFragment().withArguments(ARG_SECTION_NUMBER to sectionNumber)
        }
    }
    val listAdapter: LunchMenuAdapter = LunchMenuAdapter(emptyList<Meal>())

    lateinit var lunchMenuPresenter: LaunchMenuPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        lunchMenuPresenter = LaunchMenuPresenter(createMealService(), this, arguments.getInt(ARG_SECTION_NUMBER))
        lunchMenuPresenter.createView()
        return LunchMenuUI(listAdapter).createView(AnkoContext.create(ctx, this))
    }

    private fun createMealService(): MealService {
        val accountRepository = AccountRepository(context, GoogleCredentialWrapper(context),
                AccountPreferencesFactory(context).getPreferences())
        val mealService = MealService(ScriptClient(accountRepository.getUserCredentials()), accountRepository.getAccountName())
        return mealService
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


