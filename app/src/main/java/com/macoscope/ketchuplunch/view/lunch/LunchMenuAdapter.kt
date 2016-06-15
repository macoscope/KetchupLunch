package com.macoscope.ketchuplunch.view.lunch

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.macoscope.ketchuplunch.model.lunch.Meal
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
import org.jetbrains.anko.AnkoContext

class LunchMenuAdapter(var mealList: List<Meal>) : RecyclerView.Adapter<LaunchMenuItemViewHolder>(),
        StickyRecyclerHeadersAdapter<LunchHeaderViewHolder> {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LaunchMenuItemViewHolder? =
            LaunchMenuItemViewHolder(LunchMenuItemUI().createView(AnkoContext.create(parent!!.context, parent)))

    override fun onBindViewHolder(holder: LaunchMenuItemViewHolder?, position: Int) {
        holder!!.bind(mealList[position])
    }

    override fun getItemCount(): Int = mealList.size

    override fun onCreateHeaderViewHolder(parent: ViewGroup?): LunchHeaderViewHolder? =
            LunchHeaderViewHolder(LunchHeaderItemUI().createView(AnkoContext.create(parent!!.context, parent)))

    override fun onBindHeaderViewHolder(holder: LunchHeaderViewHolder?, position: Int) {
        holder!!.bind(mealList[position])
    }

    override fun getHeaderId(position: Int): Long = position.toLong()

}