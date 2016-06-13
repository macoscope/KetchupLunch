package com.macoscope.ketchuplunch.view.lunch

import android.content.Intent
import com.macoscope.ketchuplunch.model.lunch.Week

interface WeeksView {
    fun showWeeks(weeks: List<Week>)
    fun selectAdapterForActiveWeek(weekIndex: Long)
    fun showLoading()
    fun hideLoading()
    fun startActivityForResult(intent: Intent, requestCode: Int)
    fun startLoginActivity()
}