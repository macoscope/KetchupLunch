package com.macoscope.ketchuplunch.view.lunch

import android.app.Activity
import android.view.View
import com.macoscope.ketchuplunch.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.viewPager
import org.jetbrains.anko.verticalLayout

class LunchUI : AnkoComponent<Activity> {
    override fun createView(ui: AnkoContext<Activity>): View {
        return with(ui) {
            verticalLayout(R.style.AppTheme) {
                id = R.id.lunch_appbar

                appBarLayout {
                    //backgroundColor = ContextCompat.getColor(ctx, R.color.colorRed)

                    toolbar {
                        id = R.id.lunch_toolbar
//                        setNavigationIcon(R.drawable.ic_nav)
//                        setTitleTextColor(Color.WHITE)
//                        overflowIcon!!.colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
                    }

                    tabLayout {
                        id = R.id.lunch_tabs
//                        setTabTextColors(Color.LTGRAY, Color.WHITE)
//                        setSelectedTabIndicatorColor(Color.WHITE)
                    }
                }

                viewPager {
                    id = R.id.lunch_pager_container
//                    backgroundColor = Color.WHITE
                }
            }
        }
    }
}