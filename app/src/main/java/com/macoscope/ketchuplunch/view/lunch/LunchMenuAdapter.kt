package com.macoscope.ketchuplunch.view.lunch

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.macoscope.ketchuplunch.model.lunch.Meal
import org.jetbrains.anko.AnkoContext

class LunchMenuAdapter(var mealList: List<Meal>) : RecyclerView.Adapter<LaunchMenuItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LaunchMenuItemViewHolder? {
        return LaunchMenuItemViewHolder(LunchMenuItemUI().createView(AnkoContext.create(parent!!.context, parent)))
    }

    override fun onBindViewHolder(holder: LaunchMenuItemViewHolder?, position: Int) {
        val meal = mealList[position]
        holder!!.bind(meal)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

}