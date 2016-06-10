package com.macoscope.ketchuplunch.view.lunch

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.di.AccountModule
import com.macoscope.ketchuplunch.di.ScriptModule
import com.macoscope.ketchuplunch.di.WeekModule
import com.macoscope.ketchuplunch.model.lunch.Week
import com.macoscope.ketchuplunch.presenter.WeeksPresenter
import org.jetbrains.anko.setContentView

class LunchActivity : AppCompatActivity(), WeeksView {
    private var sectionsPagerAdapter: DaysPagerAdapter? = null
    private var viewPager: ViewPager? = null

    lateinit var weeksPresenter: WeeksPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LunchUI().setContentView(this)
        val toolbar = findViewById(R.id.lunch_toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        viewPager = findViewById(R.id.lunch_pager_container) as ViewPager?
        val tabLayout = findViewById(R.id.lunch_tabs) as TabLayout?
        tabLayout!!.setupWithViewPager(viewPager)
        weeksPresenter = WeekModule(AccountModule(this), ScriptModule(), this).provideWeeksPresenter()
        weeksPresenter.createView()
    }

    override fun onDestroy() {
        super.onDestroy()
        weeksPresenter.destroyView()
    }

    override fun showLoading() {
        throw UnsupportedOperationException()
    }

    override fun hideLoading() {
        throw UnsupportedOperationException()
    }

    override fun selectActiveWeek(index: Int) {
        sectionsPagerAdapter = DaysPagerAdapter(supportFragmentManager, index)
        viewPager!!.adapter = sectionsPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_lunch, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showWeeks(weeks: List<Week>) {
        weeks.forEach {
            Log.d("WEEK", it.name)
        }
    }
}
