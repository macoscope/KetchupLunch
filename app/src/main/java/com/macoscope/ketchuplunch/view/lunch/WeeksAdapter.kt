package com.macoscope.ketchuplunch.view.lunch

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.macoscope.ketchuplunch.R
import com.macoscope.ketchuplunch.model.lunch.Week
import org.jetbrains.anko.AnkoContext
import java.util.ArrayList

class WeeksAdapter : BaseAdapter() {

    companion object {
        data class WeekHolder(val textView: TextView){
            fun bind(week: Week){
                textView.text = week.name
            }
        }
    }

    var weeksList: List<Week> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var viewHolder: WeekHolder
        var convertViewVar = convertView
        if (convertViewVar == null) {
            convertViewVar = WeekToolbarItemUI().createView(AnkoContext.create(parent!!.context, parent))
            viewHolder = WeekHolder(convertViewVar.findViewById(R.id.week_toolbar_name) as TextView)
            convertViewVar.tag = viewHolder
        } else {
            viewHolder = convertViewVar.tag as WeekHolder
        }
        viewHolder.bind(getItem(position))
        return convertViewVar
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var viewHolder: WeekHolder
        var convertViewVar = convertView
        if (convertViewVar == null) {
            convertViewVar = WeekItemUI().createView(AnkoContext.create(parent!!.context, parent))
            viewHolder = WeekHolder(convertViewVar.findViewById(R.id.week_name) as TextView)
            convertViewVar.tag = viewHolder
        } else {
            viewHolder = convertViewVar.tag as WeekHolder
        }
        viewHolder.bind(getItem(position))
        return convertViewVar
    }

    override fun getItem(position: Int): Week = weeksList!![position]
    override fun getItemId(position: Int): Long = weeksList!![position].index
    override fun getCount(): Int = weeksList!!.size
}