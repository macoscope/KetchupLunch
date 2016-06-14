package com.macoscope.ketchuplunch.view.lunch

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.di.ScriptModule
import com.macoscope.ketchuplunch.di.WeekModule
import com.macoscope.ketchuplunch.model.lunch.Week
import com.macoscope.ketchuplunch.presenter.WeeksPresenter
import com.macoscope.ketchuplunch.view.login.LoginActivity
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity

class LunchActivity : AppCompatActivity(), WeeksView, AdapterView.OnItemSelectedListener {
    private var viewPager: ViewPager? = null
    private var progressDialog: ProgressDialog? = null
    private var weeksAdapter: WeeksAdapter? = null
    lateinit var weeksPresenter: WeeksPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LunchUI().setContentView(this)
        setSupportActionBar(findViewById(R.id.lunch_toolbar) as Toolbar?)
        viewPager = findViewById(R.id.lunch_pager_container) as ViewPager?
        selectAdapterForActiveWeek(DaysPagerAdapter.UNDEFINED_WEEK_INDEX)
        setupTabs(viewPager)
        setupPresenter()
    }

    private fun setupTabs(viewPager: ViewPager?) {
        val tabLayout = findViewById(R.id.lunch_tabs) as TabLayout?
        tabLayout!!.setupWithViewPager(viewPager)
    }

    private fun setupPresenter() {
        weeksPresenter = WeekModule(this, ScriptModule(), this).provideWeeksPresenter()
        weeksPresenter.createView()
    }

    override fun onDestroy() {
        super.onDestroy()
        weeksPresenter.destroyView()
        progressDialog?.dismiss()
    }

    override fun showLoading() {
        viewPager!!.visibility = View.GONE
        progressDialog = indeterminateProgressDialog(R.string.lunch_fetching_weeks, R.string.lunch_please_wait)
        progressDialog!!.show()
    }

    override fun hideLoading() {
        viewPager!!.visibility = View.VISIBLE
        progressDialog!!.dismiss()
    }

    override fun selectAdapterForActiveWeek(weekIndex: Long) {
        viewPager!!.adapter = DaysPagerAdapter(supportFragmentManager, weekIndex)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_lunch, menu)
        setupWeeksSpinner(menu.findItem(R.id.weeks_spinner).actionView as Spinner)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.log_out -> {
                weeksPresenter.logOut()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupWeeksSpinner(weeksSpinner: Spinner) {
        weeksAdapter = WeeksAdapter()
        weeksSpinner.adapter = weeksAdapter
        weeksSpinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        selectAdapterForActiveWeek(DaysPagerAdapter.UNDEFINED_WEEK_INDEX)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectAdapterForActiveWeek(weeksAdapter!!.getItem(position).index)
    }

    override fun showWeeks(weeks: List<Week>) {
        weeksAdapter!!.weeksList = weeks
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        weeksPresenter.onActivityResult(requestCode, resultCode)
    }

    override fun startLoginActivity() {
        startActivity<LoginActivity>()
        finish()
    }
}
