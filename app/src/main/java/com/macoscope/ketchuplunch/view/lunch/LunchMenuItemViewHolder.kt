package com.macoscope.ketchuplunch.view.lunch

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.model.lunch.Meal
import org.jetbrains.anko.find

class LunchMenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.find(R.id.lunch_menu_name)
    val count: TextView = itemView.find(R.id.lunch_menu_count)

    fun bind(meal: Meal) {
        name.text = meal.name
        count.text = meal.count.toString() + "/" + meal.totalCount
    }

}