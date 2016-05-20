package com.macoscope.ketchuplunch.view.lunch

import android.content.Intent
import com.macoscope.ketchuplunch.model.lunch.Meal

interface LunchMenuView {
    fun startActivityForResult(intent: Intent, requestCode: Int);
    fun showMealList(meals: List<Meal>)
}