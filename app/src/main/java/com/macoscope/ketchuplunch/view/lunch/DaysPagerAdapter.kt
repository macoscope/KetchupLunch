package com.macoscope.ketchuplunch.view.lunch

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class DaysPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return LunchMenuFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Mon"
            1 -> return "Tue"
            2 -> return "Wed"
            3 -> return "Thu"
            4 -> return "Fri"
            else -> return ""
        }
    }
}