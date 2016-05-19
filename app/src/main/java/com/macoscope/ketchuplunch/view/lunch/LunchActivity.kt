package com.macoscope.ketchuplunch.view.lunch

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.setContentView

class LunchActivity : AppCompatActivity() {

    private var sectionsPagerAdapter: DaysPagerAdapter? = null

    private var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_lunch)
        LunchUI().setContentView(this)
        val toolbar = findViewById(R.id.lunch_toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        sectionsPagerAdapter = DaysPagerAdapter(supportFragmentManager)
        viewPager = findViewById(R.id.lunch_pager_container) as ViewPager?
        viewPager!!.adapter = sectionsPagerAdapter

        val tabLayout = findViewById(R.id.lunch_tabs) as TabLayout?
        tabLayout!!.setupWithViewPager(viewPager)
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

}
