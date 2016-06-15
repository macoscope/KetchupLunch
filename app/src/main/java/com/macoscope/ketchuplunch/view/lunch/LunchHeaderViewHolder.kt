package com.macoscope.ketchuplunch.view.lunch

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.model.lunch.Meal
import org.jetbrains.anko.find

class LunchHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val type: TextView = itemView.find(R.id.lunch_meal_type)

    fun bind(meal: Meal) {
        type.text = meal.type
    }

}