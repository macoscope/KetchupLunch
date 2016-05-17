package com.macoscope.ketchuplunch.view

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.macoscope.ketchuplunch.R

class LunchActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: DaysPagerAdapter? = null

    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lunch)

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = DaysPagerAdapter(supportFragmentManager)

        mViewPager = findViewById(R.id.container) as ViewPager?
        mViewPager!!.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById(R.id.tabs) as TabLayout?
        tabLayout!!.setupWithViewPager(mViewPager)

        val fab = findViewById(R.id.fab) as FloatingActionButton?
        fab!!.setOnClickListener(View.OnClickListener { view ->
            Snackbar.make(view, "Replace with your own action",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show()
        })
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
