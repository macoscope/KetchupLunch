package com.macoscope.ketchuplunch.view.lunch

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.style
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

class LunchUI: AnkoComponent<Activity> {
    override fun createView(ui: AnkoContext<Activity>): View {
        return with(ui) {
                verticalLayout {
                    id = R.id.lunch_appbar
                    backgroundColor = ContextCompat.getColor(ctx, android.R.color.white)
                    orientation
                    lparams(width = matchParent, height = wrapContent)

                    toolbar {
                        id = R.id.lunch_toolbar
                        backgroundColor = ContextCompat.getColor(ctx, R.color.colorRed)
                        lparams (width = matchParent, height = wrapContent)
                        setTitleTextColor(ContextCompat.getColor(ctx, android.R.color.white))
                    }

                    tabLayout {
                        backgroundColor = ContextCompat.getColor(ctx, R.color.colorRed)
                        lparams(width = matchParent, height = wrapContent)
                        id = R.id.lunch_tabs
                        setSelectedTabIndicatorColor(ContextCompat.getColor(ctx, android.R.color.white))
                        style { view ->
                            when (view) {
                                is TextView -> view.setTextColor(android.R.color.white)
                            }
                        }
                    }

                    viewPager {
                        id = R.id.lunch_pager_container
                        lparams(width = matchParent, height = wrapContent)

                    }
                }
            }
    }
}
