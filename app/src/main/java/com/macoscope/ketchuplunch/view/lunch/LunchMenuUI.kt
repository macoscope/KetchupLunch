package com.macoscope.ketchuplunch.view.lunch

import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView

class LunchMenuUI(val listAdapter: LunchMenuAdapter) : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View {
        return with(ui) {
            frameLayout() {
                lparams(width = matchParent, height = matchParent)
                backgroundColor = Color.RED
                recyclerView {
                    backgroundColor = Color.GREEN
                    id = R.id.lunch_menu_list
                    lparams(width = matchParent, height = matchParent)
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = listAdapter
                }
            }
        }
    }
}

