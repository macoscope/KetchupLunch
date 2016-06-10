package com.macoscope.ketchuplunch.view.lunch

import android.app.ProgressDialog
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.di.AccountModule
import com.macoscope.ketchuplunch.di.ScriptModule
import com.macoscope.ketchuplunch.di.WeekModule
import com.macoscope.ketchuplunch.model.lunch.Week
import com.macoscope.ketchuplunch.presenter.WeeksPresenter
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.setContentView

class LunchActivity : AppCompatActivity(), WeeksView {
    private var sectionsPagerAdapter: DaysPagerAdapter? = null
    private var viewPager: ViewPager? = null
    private var progressDialog: ProgressDialog? = null
    private var weeksSpinner: Spinner? = null
    private var weeksAdapter: WeeksAdapter? = null
    lateinit var weeksPresenter: WeeksPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LunchUI().setContentView(this)
        setSupportActionBar(findViewById(R.id.lunch_toolbar) as Toolbar?)
        viewPager = findViewById(R.id.lunch_pager_container) as ViewPager?
        selectActiveWeek(DaysPagerAdapter.UNDEFINED_WEEK_INDEX)
        setupTabs(viewPager)
        setupPresenter()
    }

    private fun setupTabs(viewPager: ViewPager?) {
        val tabLayout = findViewById(R.id.lunch_tabs) as TabLayout?
        tabLayout!!.setupWithViewPager(viewPager)
    }

    private fun setupPresenter() {
        weeksPresenter = WeekModule(AccountModule(this), ScriptModule(), this).provideWeeksPresenter()
        weeksPresenter.createView()
    }

    override fun onDestroy() {
        super.onDestroy()
        weeksPresenter.destroyView()
    }

    override fun showLoading() {
        viewPager!!.visibility = View.GONE
        progressDialog = indeterminateProgressDialog(R.string.lunch_fetching_weeks, R.string.lunch_please_wait)
        progressDialog!!.show()
    }

    override fun hideLoading() {
        viewPager!!.visibility = View.VISIBLE
        progressDialog!!.hide()
    }

    override fun selectActiveWeek(index: Int) {
        sectionsPagerAdapter = DaysPagerAdapter(supportFragmentManager, index)
        viewPager!!.adapter = sectionsPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_lunch, menu)
        weeksSpinner = menu.findItem(R.id.weeks_spinner).actionView as Spinner
        weeksAdapter = WeeksAdapter()
        (weeksSpinner as Spinner).adapter = weeksAdapter
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return super.onOptionsItemSelected(item)
    }

    override fun showWeeks(weeks: List<Week>) {
        weeksAdapter!!.weeksList = weeks
    }
}
