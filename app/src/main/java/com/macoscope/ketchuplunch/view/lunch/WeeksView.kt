package com.macoscope.ketchuplunch.view.lunch

import com.macoscope.ketchuplunch.model.lunch.Week

interface WeeksView {
    fun showWeeks(weeks: List<Week>)
    fun selectActiveWeek(index: Long)
    fun showLoading()
    fun hideLoading()
}