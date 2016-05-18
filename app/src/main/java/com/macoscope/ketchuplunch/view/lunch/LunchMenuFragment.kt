package com.macoscope.ketchuplunch.view.lunch

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.model.ScriptClient
import com.macoscope.ketchuplunch.model.login.AccountPreferencesFactory
import com.macoscope.ketchuplunch.model.login.AccountRepository
import com.macoscope.ketchuplunch.model.login.GoogleCredentialWrapper
import com.macoscope.ketchuplunch.model.lunch.Meal
import com.macoscope.ketchuplunch.model.lunch.MealService
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.deferredObservable
import rx.schedulers.Schedulers

class LunchMenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mealList = emptyList<Meal>()
        val listAdapter = LunchMenuAdapter(mealList)
        val rootView = LunchMenuUI(listAdapter).createView(AnkoContext.create(ctx, this))
        val dayIndex = arguments.getInt(ARG_SECTION_NUMBER)


        deferredObservable {
            val accountRepository = AccountRepository(context, GoogleCredentialWrapper(context), AccountPreferencesFactory(context).getPreferences())
            val mealService = MealService(ScriptClient(accountRepository.getUserCredentials()))
            Observable.from(mealService.getUserMeals())
        }.toList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    listAdapter.mealList = it
                    listAdapter.notifyDataSetChanged()
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

class LunchMenuAdapter(var mealList: List<Meal>) : RecyclerView.Adapter<LaunchMenuItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LaunchMenuItemViewHolder? {
        return LaunchMenuItemViewHolder(LunchMenuItemUI().createView(AnkoContext.create(parent!!.context, parent)))
    }

    override fun onBindViewHolder(holder: LaunchMenuItemViewHolder?, position: Int) {
        val meal = mealList[position]
        holder!!.bind(meal)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

}

class LaunchMenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.find(R.id.lunch_menu_name)
    val count: TextView = itemView.find(R.id.lunch_menu_count)

    fun bind(meal: Meal) {
        name.text = meal.name
        count.text = meal.count.toString() + "/" + meal.totalCount

    }

}

class LunchMenuUI(val listAdapter: LunchMenuAdapter) : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View {
        return with(ui) {
            frameLayout() {
                lparams(width = matchParent, height = matchParent)
                backgroundColor = Color.RED
                recyclerView {
                    backgroundColor = Color.GREEN
                    id = R.id.lunch_menu_list
                    lparams(width = matchParent, height = matchParent)
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = listAdapter
                }
            }
        }.view()
    }

}

class LunchMenuItemUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {

            linearLayout {
                lparams(width = matchParent, height = dip(48))
                backgroundColor = Color.GRAY
                orientation = LinearLayout.HORIZONTAL

                textView { id = R.id.lunch_menu_name }
                textView { id = R.id.lunch_menu_count }
            }

        }.view()
    }
}
